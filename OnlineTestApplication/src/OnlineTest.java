import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class OnlineTest extends JFrame {
	static String studentname=" ";
	static int TOTAL=0;
	static {
		try {
			TOTAL=QuestionSeries.tally;
			studentname=JOptionPane.showInputDialog("Enter your name: ");
			if(studentname.length()<1)
				studentname="Anonymous ";
			else
				studentname=studentname.trim() + " ";
		}catch(NullPointerException e)
		{
			System.exit(0);
		}
	}
		int seconds,minutes;
		int quesnum,itemCheck,mark;
		final String TESTTITLE=QuestionSeries.testTitle;
		final int TIMELIMIT=QuestionSeries.timeLimit;
		final int PASS=QuestionSeries.passMark;
		String []answers=new String[TOTAL];
		JButton []choice_button=new JButton[5];
		JTextArea answeroptions[]=new JTextArea[4];
		JRadioButton []buttons=new JRadioButton[4];
		JTextPane pane=new JTextPane();
		JLabel student,choose,message,timecounter,testresult;
		boolean start_test,check_answer,allowRestart,finishtest;
		South panelSouth=new South();
		Center panelCenter=new Center();

		
		North panelNorth=new North();
		
		
		
		protected OnlineTest() {
			for (int i=0;i<TOTAL;i++)
				answers[i]="";
			getContentPane().setLayout(new BorderLayout());
			getContentPane().add("North",panelNorth);
			getContentPane().add("South",panelSouth);
			getContentPane().add("Center",panelCenter);
			int width=0,height=0;
			if(java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth()<799)
			{
				width=640;
				height=460;
			}
			else
			{
				width=720;
				height=540;
			}
			setSize(width,height);
			
		}
		
		class North extends JPanel
		{
			public North()
			{
				setLayout(new GridLayout(2,2));
				setBackground(new Color(230,230,255));
				student=new JLabel("\t Welcome to the online Test "+studentname);
				student.setFont(new Font("",Font.BOLD,16));
				message=new JLabel();
				message.setForeground(Color.blue);
				add(student);
				add(message);
				add(new JLabel("    "));
				add(new JLabel("    "));
				setBorder(BorderFactory.createEtchedBorder());
			}
		}
		
		class South extends JPanel
		{
			public South() {
				String []key= {" start "," next "," finish "," check next "," check previous "};
				for(int i=0;i<choice_button.length;i++)
				{
					choice_button[i]=new JButton(key[i]);
					choice_button[i].addActionListener(new ActionHandler());
					
					    
						add(choice_button[i]);
				}
					
					setBorder(BorderFactory.createEtchedBorder());
				
			}
		}
		
		class Center extends JPanel
		{
			public Center()
			{
				setLayout(new GridLayout(1,2));
				JScrollPane westpanel=new JScrollPane(pane);
				pane.setForeground(Color.red);
				pane.setFont(new Font("monospaced",0,12));
				pane.setText(QuestionSeries.info);
				pane.setEditable(false);
				JPanel eastpanel=new JPanel();
				eastpanel.setLayout(new BorderLayout());
				JPanel northEast=new JPanel();
				northEast.setBackground(new Color(230,230,255));
				eastpanel.add("North",northEast);
				JPanel westEast=new JPanel();
				westEast.setLayout(new GridLayout(6,1));
				eastpanel.add("West",westEast);
				JPanel centerEast=new JPanel();
				centerEast.setLayout(new GridLayout(6,1));
				centerEast.setBackground(new Color(255,255,200));
			    eastpanel.add("Center",centerEast);
			    timecounter=new JLabel("    There are "+TOTAL+" questions in total.");
			    timecounter.setFont(new Font("Arial",Font.BOLD,16));
			    timecounter.setBackground(Color.blue);
			    northEast.add(timecounter);
			    westEast.add(new JLabel(" "));
			    String []btns= {" A "," B "," C "," D "};
			    ButtonGroup group = new ButtonGroup();
			    for(int i=0;i<buttons.length;i++)
			    {
			    	buttons[i]=new JRadioButton(btns[i]);
			    	group.add(buttons[i]);
			    	buttons[i].addItemListener(new ItemHandler());
			    	westEast.add(buttons[i]);

			    
			    }
			    westEast.add(new JLabel());
			    choose=new JLabel("   Choose the correct answers");
			    choose.setBorder(BorderFactory.createEtchedBorder());
			    centerEast.add(choose);
			    JScrollPane panels[]=new JScrollPane[4];
			    for(int i=0;i<answeroptions.length;i++)
			    {
			    	answeroptions[i]=new JTextArea();
			    	answeroptions[i].setBorder(BorderFactory.createEtchedBorder());
			    	answeroptions[i].setEditable(false);
			    	answeroptions[i].setBackground(Color.white);
			    	answeroptions[i].setFont(new Font("",0,12));
			    	answeroptions[i].setLineWrap(true);
			    	answeroptions[i].setWrapStyleWord(true);
			    	panels[i]=new JScrollPane(answeroptions[i]);
			    	centerEast.add(panels[i]);
			    	
			    }
			    if(TIMELIMIT>0)
			    	testresult=new JLabel(studentname+", You have only "+TIMELIMIT+" minutes left to complete");
			    else
			    	testresult=new JLabel(" There is no time limit for this test");
			    testresult.setBorder(BorderFactory.createEtchedBorder());
			    centerEast.add(testresult);
			    add(westpanel);
			    add(eastpanel);
			}
		}
		
		class ActionHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent evt) 
			{
				String sourcetype=evt.getActionCommand();
				System.out.println(sourcetype);
				if(sourcetype.equals(" start "))
				{
	
					choice_button[0].setVisible(false);
					start_test=true;
					allowRestart=true;
					if(TIMELIMIT>0)
						new Timer();
					//panelSouth.remove(choice_button[1]);
					displayquestion();
				}
				if(start_test)
				{
					if(sourcetype.equals(" next "))
					{
						recordanswer();
						quesnum++;
						if(quesnum==TOTAL-1) {
							finishtest=true;
							choice_button[1].setVisible(false);}
						
						if(quesnum==TOTAL)
							quesnum=0;
						checkteststatus();
						displayquestion();
						
					}
					
					if(sourcetype.equals(" finish "))
					{
						if(finishtest)
						{
							
							recordanswer();
							quesnum=0;
							choice_button[4].setBackground(Color.lightGray);
							timecounter.setForeground(Color.blue);
							timecounter.setFont(new Font("Arial",0,14));
							start_test=false;
							check_answer=true;
							mark_ques();displayquestion();checkteststatus();calculateResult();
							
						}
						else {
							JOptionPane.showMessageDialog(null,"Attempt all questions before pressing the finish button","message",JOptionPane.INFORMATION_MESSAGE);
							
						}
					}}
					if(check_answer)
					{
						choice_button[2].setVisible(false);
						//panelSouth.remove(choice_button[2]);
						if(sourcetype.equals(" check next "))
						{
							choice_button[4].setVisible(true);
							quesnum++;
							if(quesnum==TOTAL) 
								quesnum=0;
							if(quesnum==TOTAL-1)
								choice_button[3].setVisible(false);
								
								//panelSouth.remove(choice_button[3]);
								//choice_button[3].setVisible(false);}
							displayquestion();
							mark_ques(); 
							
							checkteststatus();
						}
						
						if(sourcetype.equals(" check previous "))
						{
							choice_button[3].setVisible(true);
							quesnum--;
							if(quesnum==-1)
								quesnum=TOTAL-1;
							if(quesnum==0)
								choice_button[4].setVisible(false);
							
							displayquestion();
							mark_ques();
							checkteststatus();
							
						}
					
					validate();
				}
				
				
			} }
		
		    class Timer extends Thread implements Runnable
		    {
		    	public Timer()
		    	{
		    		new Thread(this).start();
		    	}
		    	public void run()
		    	{
		    		while(start_test)
		    		{
		    			try {
		    				Thread.sleep(1000);
		    				seconds++;
		    				if((seconds % 60==0)&&(seconds!=0))
		    				{
		    					seconds-=60;
		    					minutes++;
		    				}
		    				timecounter.setText("   Time Counter :"+minutes+" mins :"+seconds+"secs");
		    				if(minutes==TIMELIMIT)
		    				{
		    					start_test=false;
		    					endTest();
		    				}
		    			}
		    			catch(InterruptedException ex)
		    			{
		    				System.out.println(ex);
		    			}
		    		}
		    	}
				
			}
		  
		    public void checkteststatus()
		    {
		    	  
		    	if((quesnum==TOTAL-1)&&(start_test))
		    		choice_button[2].setBackground(Color.green);
		    	else
		    		choice_button[4].setBackground(Color.lightGray);
		    	if(answers[quesnum].length()>0)
		    	{
		    		for(int i=0;i<answers[quesnum].length();i++)
		    			buttons[Integer.parseInt(answers[quesnum].substring(i,i+1))-1].setSelected(true);
		    	}
		    	else
		    		for(int i=0;i<buttons.length;i++)
		    			buttons[i].setSelected(false);
		    }
		    
		    public void displayquestion()
		    {
		    	int j=quesnum+1;
		    	pane.setText(QuestionSeries.question[quesnum]);
		    	if(start_test)
		    		message.setText("Question"+j+" out of "+TOTAL);
		    	for(int i=0;i<4;i++)
		    		answeroptions[i].setText(QuestionSeries.answers[quesnum][i]);
		    	if(start_test)
		    	{
		    		String temp="";
		    		choose.setText(temp);
		    	}
		    	else
		    	{
		    		choose.setText("    Correct answers are marked in light blue.  ");
		    	}
		    }
		    public void recordanswer()
		    {
		    	String tmp="";
		    	for(int i=0;i<buttons.length;i++)
		    		if(buttons[i].isSelected())
		    			tmp+=i+1;
		    	answers[quesnum]=tmp;
		    	
		    }
		    public void endTest()
		    {
		    	message.setText("Sorry..your time is over :( You could not finish the test ");
		    	choice_button[2].setEnabled(false);
		    	choice_button[3].setEnabled(false);
		    	choice_button[4].setEnabled(false);
		    	
		    }
		    public void mark_ques()
		    {
		    	for(int i=0;i<answeroptions.length;i++)
		    		answeroptions[i].setBackground(Color.white);
		    	for(int i=0;i<QuestionSeries.choice[quesnum].length();i++)
		    		answeroptions[Integer.parseInt(QuestionSeries.choice[quesnum].substring(i,i+1))-1].setBackground(new Color(100,255,255));
		    	if(QuestionSeries.choice[quesnum].equals(answers[quesnum]))
		    		message.setText("Answer correct, well done!");
		    	else
		    		message.setText("Sorry, "+studentname+" you've gotten this one wrong :(");
		    		
		    }
		    public void calculateResult()
		    {
		    	mark=0;
		    	double temp=0.0;
		    	java.text.DecimalFormat df=new java.text.DecimalFormat("#0.#");
		    	for(int i=0;i<TOTAL;i++)
		    		if(QuestionSeries.choice[i].equals(answers[i]))
		    			mark++;
		    	temp=(double)mark;
		    	System.out.println(temp);
		    	if(temp>=PASS)
		    		testresult.setText("  Well done "+studentname.substring(0,studentname.indexOf(' '))+" you passed!");
		    	else
		    		testresult.setText("   Better luck next time "+studentname.substring(0,studentname.indexOf(' ')));
		    	student.setText("  Final score for "+studentname+" : "+mark+" out of "+TOTAL+": "+df.format(temp/TOTAL*100)+" ");
		    	new Resultwindow().show();
		    	
		    }

		class Resultwindow extends JFrame
		{
			Resultwindow()
			{
				super(studentname+" results: "+(mark>=PASS?"PASS":"FAIL"));
				Container result_cont=getContentPane();
				result_cont.setLayout(new GridLayout(TOTAL/2+3,5,2,5));
				result_cont.setBackground(new Color(255,220,255));
				result_cont.add(new JLabel(" "+" marks: "+mark+"/"+TOTAL+": "+"Percentage: "+(mark*100/TOTAL)+"%"));
				for(int i=0;i<3;i++)
					result_cont.add(new JLabel());
				String temp[]=new String[TOTAL];
				for(int i=0;i<TOTAL;i++)
				{
					if(QuestionSeries.choice[i].equals(answers[i]))
						temp[i]="correct";
					else
						temp[i]="wrong";
				}
				for(int i=0;i<TOTAL;i++)
					result_cont.add(new JLabel(" Question "+(i+1)+": "+temp[i]));
				pack();
				setLocation(100,100);
			}
		}
		class ItemHandler implements ItemListener
		{
			public void itemStateChanged(ItemEvent evt) {
				if(start_test)
				{
					for(int i=0;i<buttons.length;i++)
						if(buttons[i].isSelected())
							itemCheck++;
					if(itemCheck>QuestionSeries.n[quesnum])
							java.awt.Toolkit.getDefaultToolkit().beep();
					
			    }
			itemCheck=0;
		}}
		
	
		public static void main(String []ARGS)
		{
			OnlineTest frame=new OnlineTest();
			frame.setTitle(" "+QuestionSeries.testTitle);
			frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			frame.setVisible(true);
		}
}
		
		
	


