package com.wingsiwoo.www.view;

import com.wingsiwoo.www.service.EncryptService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;

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
     * 凯撒加密按钮
     */
    private JButton caesarButton;
    /**
     * playfair加密按钮
     */
    private JButton playfairButton;
    /**
     * hill加密按钮
     */
    private JButton hillButton;

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
        Font textFiledFont = new Font("alias", Font.PLAIN, 17);
        // textField
        textField.setEditable(true);
        textField.setBounds(170, 130, 280, 50);
        textField.setFont(textFiledFont);
        container.add(textField);

        // caesarButton
        caesarButton.setText("CAESAR");
        caesarButton.setBounds(580, 60, 110, 45);
        container.add(caesarButton);

        // playfairButton
        playfairButton.setText("PLAYFAIR");
        playfairButton.setBounds(580, 140, 110, 45);
        container.add(playfairButton);

        // hillButton
        hillButton.setText("HILL");
        hillButton.setBounds(580, 220, 110, 45);
        container.add(hillButton);
    }

    private void initField() {
        textField = new JTextField();
        caesarButton = new JButton();
        playfairButton = new JButton();
        hillButton = new JButton();
    }

    private void registerListener() {
        String text = textField.getText().trim();
        // this.caesarButton.addMouseListener();
        // this.playfairButton.addMouseListener();
        // this.hillButton.addMouseListener();
    }
}
