package com.wingsiwoo.www.view;

import com.wingsiwoo.www.service.EncryptService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
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

        // decryptButton
        decryptButton.setText("解密");
        decryptButton.setBounds(440, 260, 80, 50);
        container.add(decryptButton);
    }

    private void initField() {
        textField = new JTextField();
        keyField = new JTextField();
        buttonGroup = new ButtonGroup();
        encryptButton = new JButton();
        decryptButton = new JButton();
    }

    private void registerEncryptListener() {
        String text = textField.getText().trim();
        if(text.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.this, "待加/解密文本不可为空", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        String key = keyField.getText().trim();
        if(key.isEmpty()) {
            JOptionPane.showMessageDialog(MainFrame.this, "密钥或偏移量不可为空", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        switch (getChoice()) {
            case CAESAR :
                if(!key.matches("^-?\\d+$")) {
                    JOptionPane.showMessageDialog(MainFrame.this, "请输入纯数字偏移量", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this, "加密成功！加密后文本为：" + encryptService.caesar(text, Integer.getInteger(key)), "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                }
                break;
            case PLAYFAIR:

                break;
            case HILL:


                break;
            default:
                JOptionPane.showMessageDialog(MainFrame.this, "请先选择加密方式", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        // 如果为凯撒解密，偏移量取负（用户输入原来的偏移量）
    }

    private void registerDecryptListener() {

    }

    /**
     * 获取选择的加密算法
     */
    private String getChoice() {
        Enumeration<AbstractButton> elements = buttonGroup.getElements();
        while (elements.hasMoreElements()) {
            AbstractButton button = elements.nextElement();
            if(button.isSelected()){
                return button.getActionCommand();
            }
        }
        return null;
    }
}
