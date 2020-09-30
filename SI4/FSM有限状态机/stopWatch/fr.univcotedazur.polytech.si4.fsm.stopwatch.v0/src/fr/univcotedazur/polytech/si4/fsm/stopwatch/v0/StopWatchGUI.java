package fr.univcotedazur.polytech.si4.fsm.stopwatch.v0;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

import fr.univcotedazur.polytech.si4.fsm.stopwatch.v0.defaultsm.DefaultSMStatemachine;//import 我的状态机
import fr.univcotedazur.polytech.si4.fsm.stopwatch.v0.defaultsm.IDefaultSMStatemachine.SCInterfaceListener;


class StopWatchControlerInterfaceImplementation implements SCInterfaceListener {
    StopWatchGUI theGui;

    public StopWatchControlerInterfaceImplementation(StopWatchGUI sw) {
        theGui = sw;
    }


    @Override
    public void onInitRaised() { //初始状态
        // TODO Auto-generated method stub
        theGui.millis = 0;
        theGui.mins = 0;
        theGui.secs = 0;
        theGui.updateTimeValue1();//更新秒表
        theGui.leftButton.setText("start");
        theGui.rightButton.setText("pause");
       

    }

    @Override
    public void onDoStartRaised() {//运行状态
        // TODO Auto-generated method stub
        theGui.msTimer.start();
        theGui.msTimer1.start();
        theGui.updateTimeValue1();//更新秒表
        if (theGui.leftButton.getText().equals("start"))// 两种情况到达这个状态，分情况讨论
            theGui.leftButton.setText("stop");
        else
            theGui.rightButton.setText("pause");
    }

    @Override
    public void onDoStopRaised() {//stop状态
        // TODO Auto-generated method stub
        theGui.msTimer.stop();
        theGui.msTimer1.stop();
        theGui.updateTimeValue1();//更新秒表
        theGui.leftButton.setText("reset");
        theGui.rightButton.setText("pause");


    }

    @Override
    public void onDoPauseRaised() {//pause状态
        // TODO Auto-generated method stub
        theGui.msTimer1.stop();
        theGui.rightButton.setText("resume");
        theGui.updateTimeValue1();//更新秒表
    }
    
    @Override
    public void onShowTimeRaised() {
    	// TODO Auto-generated method stub
    	theGui.msTimer1.stop();
        theGui.updateTimeValue2(theGui.timeValue);//更新时间
    	
    }


    @Override
    public void onShowDateRaised() {
    	// TODO Auto-generated method stub
    	theGui.updateTimeValue3();//更新日期
    }


	@Override
	public void onRecodeTimeRaised() {
		// TODO Auto-generated method stub
		if(theGui.count>4) 
		{
			theGui.count=theGui.count-5;
		}
		theGui.updateTimeValue2(theGui.value[theGui.count]);
		//System.out.println(theGui.count);
		theGui.count=theGui.count+1;
	}


	@Override
	public void onDoResetRaised() {
		// TODO Auto-generated method stub
		theGui.count=0;
		for(int i=0;i<5;i++)
		theGui.value[i].setText("00::00::00");
}
}

//     /*
//    @Override
//    public void onShowTimeRaised() {//时间状态
//        theGui.msTimer1.stop();
//        theGui.updateTimeValue2();//更新时间
//        /*
//        long curMillis = theGui.date.getTime();
//        while (true) {
//            Date date1 = new Date();
//            long nowTime = date1.getTime();
//            if (nowTime - curMillis > 1000) { //时间超过1秒，跳回原来状态
//                theGui.updateTimeValue1();
//                theGui.msTimer1.start();
//                break;
//            }
//        } 
//    }
//
//    @Override
//    public void onShowDateRaised() {//日期状态
//        theGui.updateTimeValue3();//更新日期
//        long curMillis = theGui.date.getTime();
//  
//        while (true) {
//
//            Date date1 = new Date();
//            long nowTime = date1.getTime();
//            if (nowTime - curMillis > 1000) { //时间超过1秒，跳回原来状态
//                theGui.updateTimeValue1();
//                theGui.msTimer1.start();
//                break;
//            }
//        
//        }
//
//    }  */
    
//   
/**
 * Simple old style GUI for the stopWatch used as a support for the {@see <a href="http://www.i3s.unice.fr/~deantoni/teaching_resources/SI4/FSM/">Finite State Machine course</a>}
 *
 * @author Julien Deantoni, universite cote d'azur
 */
public class StopWatchGUI {

    /**
     *
     */
	private static final long serialVersionUID = -8682173885223592966L;
    protected JFrame jf;
    protected int millis, secs, mins;
    protected JButton leftButton, rightButton, middleButton,topButton;
    protected JPanel rootPanel;
    protected JLabel timeValue,value[];
    protected Timer msTimer, msTimer1;
    protected DefaultSMStatemachine theFSM;//我的状态机（将状态机和）
    protected int count;


    /**
     * construct the GUI and initialize the different value. Also initialize the {@link #msTimer}
     *
     * @param mn
     * @param se
     * @param ct
     */
    public StopWatchGUI(int mn, int se, int ct) {
        theFSM = new DefaultSMStatemachine();
        TimerService timer = new TimerService();
        theFSM.setTimer(timer);
        theFSM.init();
        theFSM.enter();
        theFSM.getSCInterface().getListeners().add(
                new StopWatchControlerInterfaceImplementation(this)
        );

        mins = mn;
        secs = se;
        millis = ct;
        count=0;


        // graphics init and listener settings
        jf = new JFrame();  //顶层容器
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭时退出程序
        jf.setSize(650, 400);
        jf.setResizable(true);
        jf.setTitle("stopwatch");
        jf.setVisible(true);
        jf.setLocationRelativeTo(null);

        rootPanel = new JPanel(null); //中间容器
        leftButton = new JButton("start");
        leftButton.setFont(new Font("Courier", Font.BOLD, 17));
        leftButton.setBounds(75, 140, 100, 70);
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theFSM.getSCInterface().raiseLeftB();
            }
        });

        middleButton = new JButton("time/date");
        middleButton.setFont(new Font("Courier", Font.BOLD, 15));
        middleButton.setBounds(225, 140, 150, 70);
        middleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theFSM.getSCInterface().raiseMiddleB();
            }
        });

        rightButton = new JButton("pause");
        rightButton.setFont(new Font("Courier", Font.BOLD, 17));
        rightButton.setBounds(425, 140, 100, 70);
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theFSM.getSCInterface().raiseRightB();
            }

        });
        
        topButton=new JButton("recodeTime");
        topButton.setFont(new Font("Courier", Font.BOLD, 17));
        topButton.setBounds(400,40,150,60);
        topButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                theFSM.getSCInterface().raiseRecodeB();
            }

        });

        value=new JLabel[5];
        for(int i=0;i<5;i++)
        {
        	value[i]=new JLabel();
        	value[i].setText("");
        	value[i].setFont(new Font("Courier", Font.BOLD, 18));
        	value[i].setBounds(50+i*110,250,100,50);
        	value[i].setText("00::00::00");
        	rootPanel.add(value[i]);
        }
        
        
        timeValue = new JLabel();
        timeValue.setFont(new Font("Courier", Font.BOLD, 40));//设置字体
        //updateTimeValue1();
        timeValue.setBounds(150, 40, 250, 50);
        rootPanel.add(timeValue);//向中间容器添加label


        rootPanel.add(leftButton);
        rootPanel.add(middleButton);
        rootPanel.add(rightButton);
        rootPanel.add(topButton);
        jf.setContentPane(rootPanel); //因为继承了JFrame,所以初始化对象，添加rootPanel

        // init a msTimer which is ready to do an action every 7 ms
        ActionListener doCountEvery7 = e -> count(7);
        ActionListener upDateTime = e -> updateTimeValue1();

        msTimer = new Timer(7, doCountEvery7); //每7ms更新count数值
        msTimer1 = new Timer(7, upDateTime);//更新count数值的同时，展现数字


    }

    public static void main(String args[]) {
        new StopWatchControlerInterfaceImplementation(new StopWatchGUI(0, 0, 0)).onInitRaised();
        //new StopWatchGUI(0,0,0);
    }

    /**
     * add {@code nbMillisec} millisecondes to the current time encoded into mins, secs, millis.
     *
     * @param nbMillisec
     */
    protected void count(int nbMillisec) {
        millis += nbMillisec;
        if (millis >= 1000) {
            secs++;
            millis = 1000 - millis;
        }
        if (secs >= 60) {
            mins++;
            secs = 60 - secs;
        }
    }

    /**
     * construct a string that represents the current time and set the {@link #timeValue} label accordingly + repaint
     */
    protected void updateTimeValue1() {  //更新秒表
        timeValue.setText((((mins / 10) == 0) ? "0" : "") + mins + ":" + (((secs / 10) == 0) ? "0" : "") + secs + ":"
                + (((millis / 10) == 0) ? "00" : (((millis / 100) == 0) ? "0" : "")) + millis);
        jf.repaint();
    }


    protected void updateTimeValue2(JLabel jb) {//更新时间
        Date date=new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh::mm::ss");
        jb.setText(ft.format(date));
        jf.repaint();
    }

    protected void updateTimeValue3() { //更新日期
        Date date=new Date();
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
        timeValue.setText(st.format(date));
        jf.repaint();
    }
}


