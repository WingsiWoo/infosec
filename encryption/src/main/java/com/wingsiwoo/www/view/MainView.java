package com.wingsiwoo.www.view;

import com.wingsiwoo.www.service.FileService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author WingsiWoo
 * @date 2021/10/6
 */
@Component
public class MainView extends JFrame {
    /**
     * 上传文件按钮
     */
    private JButton uploadButton;

    /**
     * 加密/解密按钮
     */
    private JButton okButton;

    /**
     * 密钥文本框
     */
    private JTextField keyText;

    /**
     * 上传文件
     */
    private File uploadFile;

    @Resource
    private FileService fileService;

    @PostConstruct
    private void initComponents() {
        JFrame mainFrame = new JFrame();
        uploadButton = new JButton();
        okButton = new JButton();
        keyText = new JTextField();
        Container mainFrameContentPane = mainFrame.getContentPane();

        mainFrame.setTitle("encryption");
        mainFrame.setSize(800, 480);
        // 设置主界面的初始化位置
        mainFrame.setLocationRelativeTo(mainFrame.getOwner());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrameContentPane.setLayout(null);

        Font textFiledFont = new Font("alias", Font.PLAIN, 16);
        //---- keyTextField ----
        keyText.setEditable(true);
        mainFrameContentPane.add(keyText);
        keyText.setBounds(270, 190, 300, 40);
        keyText.setFont(textFiledFont);

        //---- uploadButton ----
        uploadButton.setText("UPLOAD FILE");
        registerUploadListener();
        mainFrameContentPane.add(uploadButton);
        uploadButton.setBounds(380, 60, 110, 40);
        mainFrameContentPane.add(uploadButton);

        //---- okButton ----
        okButton.setText("ok");
        registerConfirmListener();
        mainFrameContentPane.add(okButton);
        okButton.setBounds(580, 190, 70, 40);
        mainFrameContentPane.add(okButton);
    }

    private void registerUploadListener() {
        // 用户点击上传文件按钮时则打开桌面文件夹让用户选择要上传的文件
        this.uploadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                // 获取电脑桌面文件夹
                File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
                jFileChooser.setCurrentDirectory(homeDirectory);
                jFileChooser.setDialogTitle("请选择要上传的文件");
                jFileChooser.setApproveButtonText("确定上传");
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (JFileChooser.APPROVE_OPTION == jFileChooser.showOpenDialog(null)) {
                    uploadFile = jFileChooser.getSelectedFile();
                }
            }
        });
    }

    private void registerConfirmListener() {
        // 用户点击确定按钮则调用接口
        this.okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                String key = keyText.getText();
                if (key.trim().isEmpty() || uploadFile == null) {
                    JOptionPane.showMessageDialog(MainView.this, "密钥不可为空", "ERROR", JOptionPane.ERROR_MESSAGE);
                    if (uploadFile == null) {
                        JOptionPane.showMessageDialog(MainView.this, "请先选择上传的文件", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String res = fileService.encrypt(uploadFile, key);
                    if (res.isEmpty()) {
                        JOptionPane.showMessageDialog(MainView.this, "加/解密失败", "ERROR", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainView.this, "加/解密成功,文件已成功保存到" + res, "SUCCESS", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
    }
}
