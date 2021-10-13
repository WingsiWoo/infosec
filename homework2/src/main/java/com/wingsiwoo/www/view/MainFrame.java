package com.wingsiwoo.www.view;

import java.awt.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.*;

import com.wingsiwoo.www.service.EncryptService;
import net.miginfocom.swing.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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

    }
}
