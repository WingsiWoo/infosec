package com.wingsiwoo.www.view;

import com.wingsiwoo.www.service.EncryptService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import static com.wingsiwoo.www.constant.ChoiceConstant.*;

/**
 * @author WingsiWoo
 * @date 2021/10/13
 */
@Component
public class MainFrame extends JFrame {
    @Resource
    private EncryptService encryptService;

    /**
     * 待加/解密文本框
     */
    private JTextField textField;
    /**
     * 密钥/位移文本框
     */
    private JTextField keyField;
    /**
     * 加密按钮
     */
    private JButton encryptButton;
    /**
     * 解密按钮
     */
    private JButton decryptButton;

    /**
     * 加/解密方式选择组
     */
    private ButtonGroup buttonGroup;

    @PostConstruct
    private void initComponents() {
        JFrame mainFrame = new JFrame();
        Container container = mainFrame.getContentPane();
        // create component
        initField();

        mainFrame.setTitle("ENCRYPTION");
        mainFrame.setSize(800, 400);
        // Set the initial position of the main interface
        mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        container.setLayout(null);

        // set the position of the components
        Font font = new Font("alias", Font.PLAIN, 18);
        JLabel textLabel = new JLabel();
        textLabel.setText("待加/解密文本");
        textLabel.setFont(font);
        textLabel.setBounds(85, 80, 280, 50);
        container.add(textLabel);

        JLabel keyLabel = new JLabel();
        keyLabel.setText("密文或偏移量");
        keyLabel.setFont(font);
        keyLabel.setBounds(85, 150, 280, 50);
        container.add(keyLabel);

        // textField
        textField.setEditable(true);
        textField.setBounds(270, 80, 280, 50);
        textField.setFont(font);
        container.add(textField);

        // keyField
        keyField.setEditable(true);
        keyField.setBounds(270, 150, 280, 50);
        keyField.setFont(font);
        container.add(keyField);

        // buttonGroup
        JRadioButton caesar = new JRadioButton("CAESAR");
        JRadioButton playfair = new JRadioButton("PLAYFAIR");
        JRadioButton hill = new JRadioButton("HILL");
        // add radio buttons into the same button group to mutual exclusive
        buttonGroup.add(caesar);
        buttonGroup.add(playfair);
        buttonGroup.add(hill);

        // caesar radio button
        caesar.setActionCommand("CAESAR");
        caesar.setBounds(250, 200, 120, 50);
        caesar.setFont(font);
        caesar.setSelected(true);
        container.add(caesar);

        // playfair radio button
        playfair.setActionCommand("PLAYFAIR");
        playfair.setBounds(350, 200, 120, 50);
        playfair.setFont(font);
        container.add(playfair);

        // hill radio button
        hill.setActionCommand("HILL");
        hill.setBounds(470, 200, 120, 50);
        hill.setFont(font);
        container.add(hill);

        // encryptButton
        encryptButton.setText("加密");
        encryptButton.setBounds(280, 260, 80, 50);
        container.add(encryptButton);
        registerEncryptListener();

        // decryptButton
        decryptButton.setText("解密");
        decryptButton.setBounds(440, 260, 80, 50);
        container.add(decryptButton);
        registerDecryptListener();
    }

    private void initField() {
        textField = new JTextField();
        keyField = new JTextField("ddcf");
        buttonGroup = new ButtonGroup();
        encryptButton = new JButton();
        decryptButton = new JButton();
    }

    private void registerEncryptListener() {
        this.encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = textField.getText().trim();
                String key = keyField.getText().trim();

                if (checkInputNotEmpty(text, key)) {
                    String choice = getChoice();
                    if (choice == null) {
                        JOptionPane.showMessageDialog(MainFrame.this, "请选择加密方式", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        switch (choice) {
                            case CAESAR:
                                if (!key.matches("^-?\\d+$")) {
                                    JOptionPane.showMessageDialog(MainFrame.this, "请输入纯数字偏移量", "ERROR", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(MainFrame.this, "加密成功！加密后文本为：" + encryptService.caesar(text, Integer.parseInt(key)), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case PLAYFAIR:
                                if (checkInputValid(text, key)) {
                                    JOptionPane.showMessageDialog(MainFrame.this, "加密成功！加密后文本为：" + encryptService.playfairEncrypt(text, key), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case HILL:
                                if (checkInputValid(text, key)) {
                                    if (!key.matches("[a-zA-Z]{4}")) {
                                        JOptionPane.showMessageDialog(MainFrame.this, "请输入4位纯英文字母密钥", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(MainFrame.this, "加密成功！加密后文本为：" + encryptService.hillEncrypt(text, key), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                    }
                                }
                                break;
                            default:
                                JOptionPane.showMessageDialog(MainFrame.this, "请先选择加密方式", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

    }

    private void registerDecryptListener() {
        this.decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String text = textField.getText().trim();
                String key = keyField.getText().trim();

                if (checkInputNotEmpty(text, key)) {
                    String choice = getChoice();
                    if (choice == null) {
                        JOptionPane.showMessageDialog(MainFrame.this, "请选择解密方式", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        switch (choice) {
                            case CAESAR:
                                if (!key.matches("^-?\\d+$")) {
                                    JOptionPane.showMessageDialog(MainFrame.this, "请输入纯数字偏移量", "ERROR", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    // caesar解密把原来的偏移量取负
                                    JOptionPane.showMessageDialog(MainFrame.this, "解密成功！解密后文本为：" + encryptService.caesar(text, -Integer.parseInt(key)), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case PLAYFAIR:
                                if (checkInputValid(text, key)) {
                                    JOptionPane.showMessageDialog(MainFrame.this, "解密成功！解密后文本为：" + encryptService.playfairDecrypt(text, key), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                }
                                break;
                            case HILL:
                                if (checkInputValid(text, key)) {
                                    if (!key.matches("[a-zA-Z]{4}")) {
                                        JOptionPane.showMessageDialog(MainFrame.this, "请输入4位纯英文字母密钥", "ERROR", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(MainFrame.this, "解密成功！解密后文本为：" + encryptService.hillDecrypt(text, key), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                                    }
                                }
                                break;
                            default:
                                JOptionPane.showMessageDialog(MainFrame.this, "请先选择解密方式", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取选择的加密算法
     */
    private String getChoice() {
        Enumeration<AbstractButton> elements = buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if (button.isSelected()) {
                return button.getActionCommand();
            }
        }
        return null;
    }

    private boolean checkInputNotEmpty(String text, String key) {
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.this, "待加/解密文本不可为空", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (key.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.this, "密钥或偏移量不可为空", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean checkInputValid(String text, String key) {
        if (!text.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(MainFrame.this, "PLAYFAIR加密的字符串必须为全英文字符", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!key.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(MainFrame.this, "PLAYFAIR密钥必须为全英文字符", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
