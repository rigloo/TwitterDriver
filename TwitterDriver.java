package TwitterStuff;
//This program obtains about
//30000 tweets from Donald Trump's
//twitter and analyzes for 
//retweets 
//Cs-108-2  Prog8
//******MUST USE Twitter4j library found at http://twitter4j.org/en/index.html************

//@Sergio Santana
import java.util.ArrayList;
import java.util.List;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterDriver  {
	
	
	
	
	

	public static void main(String args[]) throws TwitterException  {
		System.out.println("Program 8, Sergio Santana, masc0745\n\n");
		//configuration class for authentication keys

		ConfigurationBuilder cb = new ConfigurationBuilder();

		//Authentication keys demanded by the Twitter API in order to access Tweets and 
		//information such as the # of favs and retweets
		//these keys can be obtained by creating a Twitter
		//account
		cb.setOAuthConsumerKey("K48uW3LE0kpUZF6NBTNsxQ2xk");
		cb.setOAuthConsumerSecret("sHM5nmwHUc20r9nZpyWnRnAPruGUMqfdrA1G8HDAIBBWYyIiXF");
		cb.setOAuthAccessToken("804546174669590528-aBnPL4nfs7qcVX4LKVfTquSyYcWZslF");
		cb.setOAuthAccessTokenSecret("RTd2lRK6Q6ROiJ8TF5j4AIjrTsWAkmhJ2uHmlYPbVEIkF");

		String user="realDonaldTrump";

		//realDonaldTrump
		//explains what program will do to the user 
		System.out.println("Hello young researcher! 'Life moves pretty fast, If you don't stop \n"
				+ "and look around once in a while, you could miss it', once said in a \n"
				+ "cool little 80's movie I saw yesterday. \nBy that I mean"
				+  " let's analyze a few tweets of our President-Elect, Donald Trump.\nI believe"
				+ " that Donald Trump gets substantially more interactions on his tweets when he's\n"
				+ "straight up talking that mad-trash to Democrat Party candidate, Hillary Clinton.\n"
				+ "(Twitter API has a limit of about 3 thousand tweets from a public user, I would've\n"
				+ "analyzed his entire timeline but that is not permissible, sadly.)\n"
				+ "\nNow retrieving tweets from twitter user @"+user+"....... \n");


		//give the user some time to read the introduction before starting the loops n stuff
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {


			e.printStackTrace();
		}

		//acess the Twitter class, handles the output of tweets
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		ArrayList<Status> tweets=new ArrayList<>();

		//since we can only output 200 tweets at a time, we have to seperate them into "Pages"
		for (  int i=1; i<=16;i++      ) {
			Paging paging = new Paging(i, 200);

			List<Status> statuses = twitter.getUserTimeline(user,paging);

			for(Status m: statuses) {

				tweets.add(m);
			}

		}


		//Displays the information obtained from the methods below
		int size=getTotalsize(tweets);
		System.out.println("Number of tweets retrieved: " +size);

		System.out.println("\n\n");

		int numTweets=getTweetsWithWords(tweets);

		System.out.println("\nNumber of tweets that contain the strings Hillary, Clinton,\nor Crooked + Hillary in them: \n"+numTweets  );

		System.out.printf("\nPercentage of total tweets: \n%.2f%%\n\n", percentTweets(numTweets,size ) );

		//outputs the average favs on tweets with the following words in them

		double averageFavsWith=getAvgFavsWith(tweets,numTweets);

		System.out.printf("Average favs WITH the words Hillary, Clinton, or Crooked Hillary in them: \n%.2f\n",averageFavsWith);

		double averageRetweetsWith=getAvgRetweetsWith(tweets,numTweets);

		System.out.printf("\nAverage RETWEETS WITH the words Hillary, Clinton, or Crooked Hillary in them: \n%.2f\n",averageRetweetsWith);

		//	NOW INFORMATION ABOUT TWEETS WITHOUT STRINGS

		System.out.println("\n\n");

		numTweets=getTweetsWithoutWords(tweets);

		System.out.println("\nNumber of tweets that DON'T contain the strings Hillary, Clinton,\nor Crooked + Hillary in them: \n"+numTweets  );

		System.out.printf("\nPercentage of total tweets: \n%.2f%%\n\n", percentTweets(numTweets,size ) );

		double averageFavsWithout=getAvgFavsWithout(tweets,numTweets);
		System.out.printf("Average favs WITHOUT the words Hillary, Clinton, or Crooked Hillary in them: \n%.2f\n",averageFavsWithout);

		double averageRetweetsWithout=getAvgRetweetsWithout(tweets,numTweets);
		System.out.printf("\nAverage RETWEETS WITHOUT the words Hillary, Clinton, or Crooked Hillary in them: \n%.2f\n",averageRetweetsWithout);

		System.out.println("\n\n\n\n*************CONCLUSION:****************** \n");
		System.out.printf("Increase in FAVS when Tweets include one of the mentioned strings: \n %.2f\n",(averageFavsWith-averageFavsWithout));

		System.out.println();

		System.out.printf("Increase in RETWEETS when Tweets include one of the mentioned strings: \n %.2f\n\n",(averageRetweetsWith-averageRetweetsWithout));

		System.out.println("As you can see, the change is definitely there, although the numbers aren't as large\n"
				+ "as I would've hoped, but oh well. Keep in mind that I only obtained tweets\n"
				+ "that aren't retweets, If you aren't familiar with twitter lingo, this means\n"
				+ "that I only obtained tweets that are original, straight\nfrom the big orange cheeto man himself."
				+ " Hope you enjoyed this, I certainly didn't.");



	}
	private static int getTotalsize(ArrayList<Status> tweets) {
		int size=0;
		for(int i=0;i<tweets.size();i++)
			if(  !tweets.get(i).isRetweet())
				size=size+1;

		return size;


	}

	private static double getAvgRetweetsWithout(ArrayList<Status> tweets, int numTweets) {



		double avgRetweets=0;
		double totalRetweets=0;


		for (int i=0;i<tweets.size();i++) 

			if(!tweets.get(i).isRetweet()&& !(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )


				totalRetweets=tweets.get(i).getRetweetCount()+totalRetweets;

		avgRetweets=(double)totalRetweets/numTweets;

		return avgRetweets;
	}



	private static double getAvgRetweetsWith(ArrayList<Status> tweets, int numTweets) {
		double avgRetweets=0;
		double totalRetweets=0;


		for (int i=0;i<tweets.size();i++) 

			if(!tweets.get(i).isRetweet()&&(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )

				totalRetweets=tweets.get(i).getRetweetCount()+totalRetweets;


		avgRetweets=(double)totalRetweets/numTweets;

		return avgRetweets;

	}

	private static double  getAvgFavsWithout(ArrayList<Status> tweets, int numTweets) {

		double avgFavs=0;
		double totalFavs=0;


		for (int i=0;i<tweets.size();i++)  {

			if(!tweets.get(i).isRetweet()&& !(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )


				totalFavs=tweets.get(i).getFavoriteCount()+totalFavs;
		}

		avgFavs=(double)totalFavs/numTweets;

		return avgFavs;
	}

	private static double getAvgFavsWith( ArrayList<Status> tweets, int numTweets) {

		double avgFavs=0;
		double totalFavs=0;


		for (int i=0;i<tweets.size();i++) 

			if(!tweets.get(i).isRetweet()&&(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )

				totalFavs=tweets.get(i).getFavoriteCount()+totalFavs;


		avgFavs=(double)totalFavs/numTweets;

		return avgFavs;
	}



	private static double percentTweets(int numTweets, int size) {

		double percent;

		percent=((double)numTweets/size)*100;


		return percent;
	}

	public static int getTweetsWithWords(ArrayList<Status> tweets) {

		int tweetsWithWords=0;

		for (int i=0;i<tweets.size();i++) 


			if(!tweets.get(i).isRetweet()&&(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )

				tweetsWithWords=tweetsWithWords+1;

		return tweetsWithWords;

	}
	public static int getTweetsWithoutWords( ArrayList<Status> tweets ) {



		int tweetsWithoutWords=0;

		for (int i=0;i<tweets.size();i++)

			if(!tweets.get(i).isRetweet()&& !(tweets.get(i).getText().contains("Hillary")||tweets.get(i).getText().contains("Clinton")||tweets.get(i).getText().contains("hillary")||
					tweets.get(i).getText().contains("Crooked Hillary")) )


				tweetsWithoutWords=tweetsWithoutWords+1;

		return tweetsWithoutWords;
	}


}
