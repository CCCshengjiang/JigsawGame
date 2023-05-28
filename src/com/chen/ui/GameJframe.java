package com.chen.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJframe extends JFrame implements KeyListener, ActionListener {
    private int[][] data = new int[4][4];

    private int x = 0;
    private int y = 0;

    private String path="image\\sport\\sport3\\";

    private int[][] win =new int[][]{
        {1,2,3,4},
        {5,6,7,8},
        {9,10,11,12},
        {13,14,15,0}
    };

    int step=0;

    JMenuItem replayItem = new JMenuItem("重新开始");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");

    JMenuItem accountUs = new JMenuItem("联系我");

    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    JMenu changeImage = new JMenu("更换图片");

    //主界面
    public GameJframe() {

        initJFrame();

        initJMenuBar();

        initData();

        initImage();

        this.setVisible(true);
    }

    //图片数据初始化
    private void initData() {
        int[] arr = {0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int index = r.nextInt(arr.length);

            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = arr[i];
        }

    }

    //图片初始化
    private void initImage() {
        this.getContentPane().removeAll();

        if(victorGame()){
            JLabel winJLabel = new JLabel(new ImageIcon("image\\win.png"));
            winJLabel.setBounds(203,283,197,73);
            this.getContentPane().add(winJLabel);
        }

        JLabel stepCount=new JLabel("步数:"+step);
        stepCount.setBounds(50,30,100,20);
        this.getContentPane().add(stepCount);

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                int num = data[j][i];
                JLabel jLabel = new JLabel(new ImageIcon(path+ num + ".jpg"));
                jLabel.setBounds(105 * i + 83, 105 * j + 134, 105, 105);
                jLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
                this.getContentPane().add(jLabel);
            }
        }

        JLabel bg = new JLabel(new ImageIcon("image\\background.png"));
        bg.setBounds(40, 40, 508, 560);
        this.getContentPane().add(bg);

        this.getContentPane().repaint();

    }

    //菜单初始化
    private void initJMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();

        JMenu functionJMenu = new JMenu("功能");
        JMenu aboutJMenu = new JMenu("关于");


        functionJMenu.add(changeImage);
        functionJMenu.add(replayItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        aboutJMenu.add(accountUs);

        replayItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountUs.addActionListener(this);

        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);

        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        this.setJMenuBar(jMenuBar);
    }

    //界面初始化
    private void initJFrame() {
        this.setSize(603, 680);
        this.setTitle("拼图小游戏 v1.0");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(victorGame()){
            return;
        }
        if(e.getKeyCode()==65){
            System.out.println("查看完整图片");
            this.getContentPane().removeAll();
            JLabel seeAll=new JLabel(new ImageIcon(path+"all.jpg"));
            seeAll.setBounds(83,134,420,420);
            this.getContentPane().add(seeAll);

            JLabel bg = new JLabel(new ImageIcon("image\\background.png"));
            bg.setBounds(40, 40, 508, 560);
            this.getContentPane().add(bg);

            this.getContentPane().repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(victorGame()){
            return;
        }
        //37left
        if(e.getKeyCode()==37){
            if(y==3){
                return;
            }
            System.out.println("左");
            data[x][y]=data[x][y+1];
            data[x][y+1]=0;
            y++;
            step++;
        }else if(e.getKeyCode()==38){
            if(x==3){
                return;
            }
            System.out.println("上");
            data[x][y]=data[x+1][y];
            data[x+1][y]=0;
            x++;
            step++;
        }else if(e.getKeyCode()==39){
            if(y==0){
                return;
            }
            System.out.println("右");
            data[x][y]=data[x][y-1];
            data[x][y-1]=0;
            y--;
            step++;
        }else if(e.getKeyCode()==40){
            if(x==0){
                return;
            }
            System.out.println("下");
            data[x][y]=data[x-1][y];
            data[x-1][y]=0;
            x--;
            step++;
        }else if(e.getKeyCode()==87){
            data =new int[][]{
                    {1,2,3,4},
                    {5,6,7,8},
                    {9,10,11,12},
                    {13,14,15,0}
            };
        }

        initImage();
    }

    private boolean victorGame(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(data[i][j]!=win[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==replayItem){
            System.out.println("重新开始");
            step=0;
            initData();
            initImage();
        }else if(e.getSource()==reLoginItem){
            System.out.println("重新登陆");
            this.setVisible(false);
            new LoginJframe();
        }else if(e.getSource()==closeItem){
            System.out.println("关闭游戏");
            System.exit(0);
        }else if(e.getSource()==accountUs){
            System.out.println("公众号");
            JDialog jDialog=new JDialog();
            JLabel jLabel=new JLabel(new ImageIcon("image\\about.jpg"));
            jLabel.setBounds(0,0,258,258);
            jDialog.getContentPane().add(jLabel);
            jDialog.setSize(344,344);
            jDialog.setAlwaysOnTop(true);
            jDialog.setLocationRelativeTo(null);
            jDialog.setModal(true);
            jDialog.setVisible(true);
        }
        Random r=new Random();
        if(e.getSource()==girl){
            path="image\\girl\\girl"+(r.nextInt(13)+1)+"\\";
            initData();
            initImage();
        }else if(e.getSource()==animal){
            path="image\\animal\\animal"+(r.nextInt(8)+1)+"\\";
            initData();
            initImage();
        }else if(e.getSource()==sport){
            path="image\\sport\\sport"+(r.nextInt(10)+1)+"\\";
            initData();
            initImage();
        }
    }
}

