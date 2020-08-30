
public class QuestionSeries {
	static String info ="Online test\n Instructions: There are 5 questions in this test and 2 minutes to complete. You should complete the test within that time limit, otherwise, the test stops automatically. Also, please choose the correct answers at the first attempt itself because there's NO GOING BACK. Best of luck!";
	static String []question = {
			"Question 1: What is the capital of India?",
			"Question 2: What is the capital of China?",
			"Question 3: What is the name of the current global pandemic outbreak?",
			"Question 4: What is the name of Taylor Swift's cat?",
			"Question 5: How many colours are there in the rainbow?"};
	static String [][]answers= {{"\nMumbai\n","\nChennai\n","\nNew Delhi\n","\nHyderabad\n"},
								{"\nBeijing\n","\nCairo\n","\nWonderland\n","\nMoscow\n"},
								{"\nSwine flu\n","CRISPR/CAS9\n","\nCOVID19\n","\nDengue\n"},
								{"\nLizzy\n","\nMeredith\n","\nPearl\n","\nBonnie\n"},
								{"\n5\n","\n9\n","\n8\n","\n7\n"}};
	static int []n= {1,1,1,1,1};
	static String []choice= {"3","1","3","2","4"};
	static int tally=choice.length;
	static String testTitle="Online test";
	static int passMark=4;
	static int timeLimit=2;
 }
