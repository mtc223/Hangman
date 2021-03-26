
package hangman;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hangman{

    static int playingState = 0;
    private static final ArrayList list = new ArrayList();
    
    static class you{
    
    private static final int buttonWidth = 40;
    private static final int buttonHeight = 40;
    private static button[] buttons;
    private final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String word;
    private static int wordLength;
    private static String guess;
    private static int guessInt;
    
    public you(){
        
        try{	
            InputStream is = Hangman.class.getResourceAsStream("common.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            boolean done = false;
            String name;
            while(! done){
                name = in.readLine();
                if (name == null)
                    done = true;
                else
                    list.add(name);	
            }
        is.close(); 			
        } // end try
        catch (IOException exception){
        }
        buttons = new button[26];
        int h = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 10; j++){
                buttons[h] = new button(20+(j*46),300+(i*50),String.valueOf(alphabet.charAt(h)),true);
                h++;
                if(i == 2){
                    if(j == 5)
                        j = 10;
                }
            }
        }
        Random r = new Random();
        word = list.get(r.nextInt(list.size()-1)).toString();
        while(word.length() > 10 && !word.contains(" ")){
            word = list.get(r.nextInt(list.size()-1)).toString();
        }
        wordLength = word.length();
        guess = "";
        guessInt = 0;
        for(int i = 0; i < wordLength; i++){
            guess += " ";
        }
    }
    
    public void reset(){
        for(int i = 0; i < 26; i++){
            buttons[i].setValid(true);
        }
        for(int i = 0; i < wordLength; i++){
            guess += " ";
        }
        guessInt = 0;
    }
    
    static class board extends JPanel implements MouseListener {
        
        public static Point mouse;
        public int choice = 0;
        
        public board(){
            
            mouse = new Point(1,1);
            addMouseListener(this);
        }
        
        public void updateMousePos(JFrame f){
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            if(b.getX() >= f.getLocation().getX() && b.getX() <= f.getLocation().getX()+f.getWidth() && 
               b.getY() >= f.getLocation().getY() && b.getY() <= f.getLocation().getY()+f.getHeight() ){
               mouse.setLocation(b.getX()-f.getLocation().getX(),b.getY()-f.getLocation().getY()-20);
            }
            choice = checkChoice();
            f.repaint();
        }
        
        public int checkChoice(){
            for(int i = 0; i < 26; i++){
                if(mouse.getX() >= buttons[i].getX() && mouse.getX() <= buttons[i].getX()+buttonWidth &&
                   mouse.getY() >= buttons[i].getY() && mouse.getY() <= buttons[i].getY()+buttonHeight){
                   return i;
                }
            }
            return 26;
        }
        
        public void updateMousePos2(JFrame f){
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            if(b.getX() >= f.getLocation().getX() && b.getX() <= f.getLocation().getX()+f.getWidth() && 
               b.getY() >= f.getLocation().getY() && b.getY() <= f.getLocation().getY()+f.getHeight() ){
               mouse.setLocation(b.getX()-f.getLocation().getX(),b.getY()-f.getLocation().getY()-20);
            }
            choice = checkChoice2();
            f.repaint();
        }
        
        public int checkChoice2(){
            if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 150 && mouse.getY() <= 200){
                return 1;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 230 && mouse.getY() <= 280){
                return 2;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 310 && mouse.getY() <= 360){
                return 3;
            }
            return 0;
        }
        
        Color maroon = new Color(157,0,0);
        
        @Override
        public void paint(Graphics gr){
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;
            g.setStroke(new BasicStroke(3));
            Font f = new Font("SansSerif Bold", Font.BOLD, 20);
            g.setFont(f);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            Hangman.menu m = new Hangman.menu();
            g.setColor(Color.black);
            g.fillRect(0,0,500,500);
            g.setColor(Color.white);
            g.fillRect(10,10,480,460);
            g.setColor(Color.black);
            g.drawLine(300,50,300,100);
            g.drawLine(400,50,300,50);
            g.drawLine(400,50,400,210);
            g.drawLine(325,210,425,210);
            g.setColor(maroon);
            if(guessInt >= 1){
                g.drawOval(290, 100, 20, 20);
                if(guessInt >= 2){
                    g.drawLine(300, 120, 300, 150);
                    if(guessInt >= 3){
                        g.drawLine(285,130,300,135);
                        if(guessInt >= 4){
                            g.drawLine(315,130,300,135);
                            if(guessInt >= 5){
                                g.drawLine(300,150,290,170);
                                if(guessInt >= 6){
                                    g.drawLine(300,150,310,170);
                                    if(guessInt >= 7){
                                        g.drawLine(290,170,285,167);
                                        if(guessInt >= 8){
                                            g.drawLine(310,170,315,167);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            g.setColor(Color.black);
            if(playingState == 2){
            if(choice != 26){
                g.fillRect(buttons[choice].getX()-5,buttons[choice].getY()-5,buttonWidth+10,buttonHeight+10);
            }
            }
            for(int i = 0; i < 26; i++){
                int x = buttons[i].getX();
                int y = buttons[i].getY();
                g.setColor(Color.red);
                g.fillRect(x,y,buttonWidth,buttonHeight);
                g.setColor(Color.black);
                g.drawRect(x,y,buttonWidth,buttonHeight);
                g.setColor(Color.white);
                g.drawString(buttons[i].getLetter(), x+12, y+23);
                if(!buttons[i].getValid()){
                    g.setColor(Color.white);
                    g.drawLine(x, y, x+buttonWidth, y+buttonHeight);
                    g.drawLine(x+buttonWidth, y, x, y+buttonHeight);
                }
            }
            for(int i = 1; i < wordLength+1; i++){
                g.setColor(Color.black);
                g.drawLine(35*i, 250,(35*i)+20,250);
                g.setColor(Color.red);
                g.drawString(String.valueOf(guess.charAt(i-1)), (int)(((35*i)+((35*i)+20))/2)-10, 240);
            }
            if(playingState == 3){
                g.setColor(Color.white);
                g.fillRect(15,280,470,180);
                if(choice == 1){
                    g.setColor(Color.black);
                    g.fillRect(170, 140, 160, 70);
                    if(enter){
                        playingState = 2;
                    }
                }
                else if(choice == 2){
                    g.setColor(Color.black);
                    g.fillRect(170, 220, 160, 70);
                    if(enter){
                        playingState = 4;
                    }
                }
                else if(choice == 3){
                    g.setColor(Color.black);
                    g.fillRect(170, 300, 160, 70);
                    if(enter){
                        System.exit(0);
                    }
                }
                g.setColor(Color.red);
                g.fillRect(180, 150, 140, 50);
                g.fillRect(180, 230, 140, 50);
                g.fillRect(180, 310, 140, 50);
                g.setColor(Color.white);
                g.drawString("You Guess", 200, 170);
                g.drawString("CPU Guesses", 183, 250);
                g.drawString("Quit", 210, 330);
                g.setColor(Color.black);
                g.fillRect(90,40,320,80);
                g.setColor(Color.red);
                g.fillRect(100,50,300,60);
                g.setColor(Color.white);
                g.drawString("The word was " + word,100,70);
            }
            enter = false;
        }
        
        boolean enter = false;
        
        @Override
        public void mouseClicked(MouseEvent e) {
            Hangman.menu m = new Hangman.menu();
            if(playingState == 3){
                enter = true;
            }
            if(playingState == 2){
            if(choice != 26){
            if(buttons[choice].getValid() == true){
                buttons[choice].setValid(false);
                boolean guessed = false;
                for(int i = 0; i < wordLength; i++){
                    if(buttons[choice].getLetter().equalsIgnoreCase(String.valueOf(word.charAt(i)))){
                        guess = guess.substring(0,i) + buttons[choice].getLetter() + guess.substring(i+1);
                        guessed = true;
                    }
                }
                if(!guessed){
                    guessInt++;
                }
                if(guessInt == 8){
                    playingState = 3;
                }
                if(!guess.contains(" ")){
                    playingState = 3;
                }
                
            }
            }
            
        }
        }    
            
        @Override
        public void mousePressed(MouseEvent e) {
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    //button(int x, int y, String letter, boolean valid);
    
    static class button {
        
        private int x;
        private int y;
        private String letter;
        private boolean valid;
        
        public button(int a_x, int a_y, String a_letter, boolean a_valid){
            x = a_x;
            y = a_y;
            letter = a_letter;
            valid = a_valid;
        }
        
        public void setX(int a_x){
            x = a_x;
        }
        
        public void setY(int a_y){
            y = a_y;
        }
        
        public void setLetter(String a_letter){
            letter = a_letter;
        }
        
        public void setValid(boolean a_valid){
            valid = a_valid;
        }
        
        public int getX(){
            return x;
        }
        
        public int getY(){
            return y;
        }
        
        public String getLetter(){
            return letter;
        }
        
        public boolean getValid(){
            return valid;
        }
        
    }
    }
    
    static class cpu{
        
    static ArrayList list = new ArrayList();
    static ArrayList oglist = new ArrayList();
    static ArrayList <String> Word = new ArrayList <String>();
    static String guess = "";
    static String guessed = "";
    static int guessCount = 0;
    static String alphabet = "abcdefghijklmnopqrstuvwxyz";
    static int length = 0;
    static boolean enter = false;
    static int choice = 1;
    
    public cpu(){
        
        try{	
                InputStream is = Hangman.class.getResourceAsStream("wordsEn.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                boolean rdone = false;
                String name;
                while(!rdone){
            {
            name = in.readLine();
            if(name != null){
            oglist.add(name);
            }
            else{
                rdone = true;
            }
            }
	}
	is.close();	
        } // end try
        catch (IOException exception){}
    }
    
    public void proccess(int length){
        ArrayList tempList = new ArrayList();
            int i = 0;
            while(i < list.size()){
                String word = list.get(i).toString();
                if(word.length() == length+1){
                    tempList.add(word);
                }
                i++;
            }
            list = tempList;
    }
    
    public String findMostCommon(){
        int[] occured = new int[26];
        for(int i = 0; i < 26; i++){
            for(int j = 0; j < list.size(); j++){
                String word = list.get(j).toString();
                if(word.contains(String.valueOf(alphabet.charAt(i)))){
                    occured[i]++;
                }
            }
        }
        for(int i = 0; i < guessed.length(); i++){ 
            occured[alphabet.indexOf(guessed.charAt(i))] = 0;
        }
        int common = 0;
        for(int i = 0; i < 26; i++){
            if(occured[i] > occured[common]){
                common = i;
            }
        }
        return String.valueOf(alphabet.charAt(common));
    }
    
    public void refineList(String bad){
        if(!bad.equals("")){
            for(int i = list.size() - 1; i >= 0; i--){
                String listWord = list.get(i).toString();
                if(listWord.contains(bad)){
                    list.remove(i);
                }
            }
        }
        for(int i = 0; i < Word.size(); i++){
            String letter = Word.get(i);
            if(!letter.equals(" ")){
                for(int j = list.size() -1; j >= 0; j--){
                    String listWord = list.get(j).toString();
                    if(listWord.charAt(i) != letter.charAt(0)){
                        list.remove(j);
                    }
                }
            }
        }
       
    }
    
    static boolean msgGuess = false;
    static int guessChoice = 0;
    static boolean msgPlace = false;
    static boolean next = false;
    static boolean msgBad = false;
    static boolean done = false;
    
    public void reset(){
        msgGuess = false;
        guessChoice = 0;
        msgPlace = false;
        //next = false;
        msgBad = false;
        done = false;
        list.clear();
        list = oglist;
        guess = "";
        guessed = "";
        guessCount = 0;
        length = 0;
        enter = false;
        choice = 1;
        Word.clear();
    }
    
    public void handleClass(JFrame f,Hangman.cpu.board b){
        reset();
        while(playingState == 4){
            b.updateMousePos(f);
        }
        proccess(length);
        boolean notguessed = true;
        for(int i = 0; i <= length; i++){
            Word.add(" ");
        }
        msgGuess = true;
        enter = false;
        while(notguessed){
            b.updateMousePos(f);
            guess = findMostCommon();
            String badGuess = "";
            guessed += guess;
            while(msgGuess){
                b.updateMousePos(f);
                if(enter){
                    if(guessChoice == 1){
                        msgPlace = true;
                        msgGuess = false;
                    }
                    else if(guessChoice == 2){
                        msgGuess = false;
                        badGuess = guess;
                        guessCount++;
                    }
                }
                enter = false;
            }
            while(msgPlace){
                b.updateMousePos(f);
                if(enter){
                    if(choice != 14 && choice <= Word.size()){
                        Word.set(choice, guess);
                    }
                    if(next){
                        msgPlace = false;
                    }
                    next = false;
                }
                enter = false;
            }
            refineList(badGuess);
            notguessed = false;
            for(int i = 0; i < Word.size(); i++){
                if(Word.get(i).equals(" "))
                    notguessed = true;
            }
            if(!notguessed){
                done = true;
            }
            if(list.isEmpty()){
                msgBad = true;
                done = true;
                notguessed = false;
            }
            list.toString();
            if(list.size() == 1){
                notguessed = false;
                done = true;
            }
            if(guessCount == 8){
                msgBad = true;
                done = true;
                notguessed = false;
            }
            msgGuess = true;
        }
        msgGuess = false;
        while(done){
            b.updateMousePos(f);
        }
    }
    
    static class board extends JPanel implements MouseListener {
        
        public static Point mouse;
        public static button[] buttons;
        
        public board(){
            
            mouse = new Point(1,1);
            addMouseListener(this);
            buttons = new button[14];
            for(int i = 0; i < 14; i++){
                buttons[i] = new button(i*34+20,300," ",true);
            }
        }
        
        public void updateMousePos(JFrame f){
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            if(b.getX() >= f.getLocation().getX() && b.getX() <= f.getLocation().getX()+f.getWidth() && 
               b.getY() >= f.getLocation().getY() && b.getY() <= f.getLocation().getY()+f.getHeight() ){
               mouse.setLocation(b.getX()-f.getLocation().getX(),b.getY()-f.getLocation().getY()-20);
            }
            if(msgGuess){
            if(mouse.getX() >= 75 && mouse.getX() <= 150 && mouse.getY() >= 175 && mouse.getY() <= 230){
                guessChoice = 1;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 250 && mouse.getY() >= 175 && mouse.getY() <= 230){
                guessChoice = 2;
            }
            else{
                guessChoice = 0;
            }
            }
            if(msgPlace){
                next = (mouse.getX() >= 45 && mouse.getX() <= 295 && mouse.getY() >= 175 && mouse.getY() <= 230);
            }
            choice = checkChoice();
            if(done)
                choice = checkChoice2();
            f.repaint();
        }
        
        public int checkChoice(){
            for(int i = 0; i < 14; i++){
                if(mouse.getY() <= 300 && mouse.getY() >= 270){
                    if(mouse.getX() >= buttons[i].getX() && mouse.getX() <= buttons[i].getX()+20){
                        return i;
                    }
                }
            }
            return 14;
        }
        
        public int checkChoice2(){
            if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 150 && mouse.getY() <= 200){
                return 1;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 230 && mouse.getY() <= 280){
                return 2;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 310 && mouse.getY() <= 360){
                return 3;
            }
            return 0;
        }
        
        final Color maroon = new Color(153,0,0);
        
        public void paintman(Graphics2D g){
            g.setColor(maroon);
            if(guessCount >= 1){
                g.drawOval(290, 100, 20, 20);
                if(guessCount >= 2){
                    g.drawLine(300, 120, 300, 150);
                    if(guessCount >= 3){
                        g.drawLine(285,130,300,135);
                        if(guessCount >= 4){
                            g.drawLine(315,130,300,135);
                            if(guessCount >= 5){
                                g.drawLine(300,150,290,170);
                                if(guessCount >= 6){
                                    g.drawLine(300,150,310,170);
                                    if(guessCount >= 7){
                                        g.drawLine(290,170,285,167);
                                        if(guessCount >= 8){
                                            g.drawLine(310,170,315,167);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        @Override
        public void paint(Graphics gr){
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;
            g.setStroke(new BasicStroke(3));
            Font f = new Font("SansSerif Bold", Font.BOLD, 20);
            g.setFont(f);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.fillRect(0,0,500,500);
            g.setColor(Color.white);
            g.fillRect(10,10,480,460);
            g.setColor(Color.black);
            g.drawLine(300,50,300,100);
            g.drawLine(400,50,300,50);
            g.drawLine(400,50,400,210);
            g.drawLine(325,210,425,210);
            if(playingState == 4){
                g.setColor(Color.red);
                g.drawString("Think of a word...", 70,100);
                g.drawString("How many letters?", 70, 120);
            for(int i = 0; i < 14; i++){
                g.setColor(Color.black);
                g.drawLine(buttons[i].getX(),buttons[i].getY(),buttons[i].getX()+20,buttons[i].getY());
            }
            if(choice != 14){
            g.setColor(Color.red);
            for(int i = 0; i <= choice; i++){
                try{
                    g.drawRect(buttons[i].getX()-7, buttons[i].getY()-30, 34, 34);
                    g.drawString("" + (i+1), buttons[i].getX(), buttons[i].getY()-35);
                }
                catch(java.lang.ArrayIndexOutOfBoundsException e){}
            }
            }
            }
            if(playingState == 5){
                paintman(g);
                for(int i = 0; i <= length; i++){
                    g.setColor(Color.black);
                    g.drawLine(buttons[i].getX(),buttons[i].getY(),buttons[i].getX()+20,buttons[i].getY());
                }
                try{
                for(int i = 0; i < Word.size(); i++){
                if(!Word.get(i).equals(" ")){
                    g.drawString(Word.get(i).toUpperCase(),buttons[i].getX()+5, buttons[i].getY()-5);
                }
                }
                }
                catch(java.lang.NullPointerException n){}
                
                if(msgGuess){
                g.setColor(Color.red);
                g.drawString("CPU guesses "+guess.toUpperCase(), 70, 100);
                g.drawString("Is " + guess.toUpperCase() + " in the word?", 70, 120);
                g.setColor(Color.black);
                
                if(guessChoice == 1){
                    g.fillRect(60,160,90,70);
                }
                else if(guessChoice == 2){
                    g.fillRect(170,160,90,70);
                }
                g.setColor(Color.red);
                g.fillRect(70,170,70,50);
                g.fillRect(180,170,70,50);
                g.setColor(Color.white);
                g.drawString("YES", 80,200);
                g.drawString("NO", 190,200);
                }
                if(msgPlace){
                if(next){
                    g.setColor(Color.black);
                    g.fillRect(30,160,270,70);
                }
                g.setColor(Color.red);
                g.fillRect(40,170,250,50);
                g.setColor(Color.white);
                g.drawString("No more "+ guess.toUpperCase() + "'s, next letter.", 45,200);
                
                try{
                g.setColor(Color.red);
                g.drawString("Where are the "+guess.toUpperCase() + "'s?", 70, 100);
                if(choice < Word.size()){
                g.setColor(Color.red);
                g.drawRect(buttons[choice].getX()-7, buttons[choice].getY()-30, 34, 34);
                }
                }
                catch(java.lang.ArrayIndexOutOfBoundsException e){}
                }
                if(done){
                    g.setColor(Color.white);
                    g.fillRect(15,280,470,180);
                    g.fillRect(50,50,220,220);
                    if(choice == 1){
                    g.setColor(Color.black);
                    g.fillRect(170, 140, 160, 70);
                    if(enter){
                        playingState = 2;
                        done = false;
                        msgBad = false;
                    }
                    enter = false;
                    }
                    else if(choice == 2){
                    g.setColor(Color.black);
                    g.fillRect(170, 220, 160, 70);
                    if(enter){
                        playingState = 4;
                        done = false;
                        msgBad = false;
                    }
                    enter = false;
                    }
                    else if(choice == 3){
                    g.setColor(Color.black);
                    g.fillRect(170, 300, 160, 70);
                    if(enter){
                        System.exit(0);
                    }
                    }
                    g.setColor(Color.red);
                    g.fillRect(180, 150, 140, 50);
                    g.fillRect(180, 230, 140, 50);
                    g.fillRect(180, 310, 140, 50);
                    g.setColor(Color.white);
                    g.drawString("You Guess", 200, 170);
                    g.drawString("CPU Guesses", 183, 250);
                    g.drawString("Quit", 210, 330);
                    g.setColor(Color.black);
                    g.fillRect(90,40,320,80);
                    g.setColor(Color.red);
                    g.fillRect(100,50,300,60);
                    g.setColor(Color.white);
                    if(msgBad){
                    g.drawString("CPU gives up",160,70);
                    }
                    else{
                    g.drawString("CPU's final guess is " + list.get(0),100,70);
                    }
                }
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
        if(playingState == 4){
            if(choice != 14){
                length = choice;
                playingState = 5;
            }
        }
        if(playingState == 5){
        enter = true;
        }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
    static class button {
        
        private int x;
        private int y;
        private String letter;
        private boolean valid;
        
        public button(int a_x, int a_y, String a_letter, boolean a_valid){
            x = a_x;
            y = a_y;
            letter = a_letter;
            valid = a_valid;
        }
        
        public void setX(int a_x){
            x = a_x;
        }
        
        public void setY(int a_y){
            y = a_y;
        }
        
        public void setLetter(String a_letter){
            letter = a_letter;
        }
        
        public void setValid(boolean a_valid){
            valid = a_valid;
        }
        
        public int getX(){
            return x;
        }
        
        public int getY(){
            return y;
        }
        
        public String getLetter(){
            return letter;
        }
        
        public boolean getValid(){
            return valid;
        }
        
    }
    }
    
    static class menu extends JPanel implements MouseListener {
        
        public static Point mouse;
        public static int choice = 1;
        
        public menu(){
            
            mouse = new Point(1,1);
            addMouseListener(this);
        }
        
        public void updateMousePos(JFrame f){
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            if(b.getX() >= f.getLocation().getX() && b.getX() <= f.getLocation().getX()+f.getWidth() && 
               b.getY() >= f.getLocation().getY() && b.getY() <= f.getLocation().getY()+f.getHeight() ){
               mouse.setLocation(b.getX()-f.getLocation().getX(),b.getY()-f.getLocation().getY()-20);
            }
            checkChoice();
            f.repaint();
        }
        
        public void checkChoice(){
            if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 150 && mouse.getY() <= 200){
                choice = 1;
            }
            else if(mouse.getX() >= 180 && mouse.getX() <= 320 && mouse.getY() >= 230 && mouse.getY() <= 280){
                choice = 2;
            }
            else {
                choice = 0;
            }
        }
        
        @Override
        public void paint(Graphics gr){
            super.paint(gr);
            Graphics2D g = (Graphics2D) gr;
            g.setStroke(new BasicStroke(5));
            Font f = new Font("SansSerif Bold", Font.BOLD, 20);
            g.setFont(f);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.black);
            g.fillRect(0,0,500,500);
            g.setColor(Color.white);
            g.fillRect(10,10,480,460);
            g.setColor(Color.red);
            g.drawString("Hangman!!!", 190, 100);
            if(choice == 1){
                g.setColor(Color.black);
                g.fillRect(170, 140, 160, 70);
            }
            else if(choice == 2){
                g.setColor(Color.black);
                g.fillRect(170, 220, 160, 70);
            }
            g.setColor(Color.red);
            g.fillRect(180, 150, 140, 50);
            g.fillRect(180, 230, 140, 50);
            g.setColor(Color.white);
            g.drawString("You Guess", 200, 170);
            g.drawString("CPU Guesses", 183, 250);
            g.setColor(Color.black);
            g.drawLine(100,70,100,180);
            g.drawLine(100,70,400,70);
            g.drawLine(400,70,400,400);
            g.drawLine(450,400,250,400);
            Color maroon = new Color(153,0,0);
            g.setColor(maroon);
            g.drawOval(80,180,40,40);
            g.drawLine(100,220,100,270);
            g.drawLine(100,240,80,230);
            g.drawLine(100,240,120,230);
            g.drawLine(100,270,80,300);
            g.drawLine(100,270,120,300);
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if(choice == 1){
                playingState = 2;
            }
            else if(choice == 2){
                playingState = 4;
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    public static void main(String[] args) throws InterruptedException{
        
        Hangman h = new Hangman();
        JFrame frame = new JFrame("Hangman!!!");
        Hangman.menu m = new Hangman.menu();  
        m.setFocusable(true);
        m.requestFocusInWindow();
        frame.add(m);
        frame.setSize(500,500);
        frame.setLocation(520,185);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        playingState = 1;
        
        
        while(playingState == 1){
            m.updateMousePos(frame);
        }
        while(playingState != 0){
        if(playingState == 2){
            Hangman.you.board b = new Hangman.you.board();  
            Hangman.you u = new Hangman.you();
            b.setFocusable(true);
            b.requestFocusInWindow();
            frame.remove(m);
            frame.add(b);
            frame.setVisible(true);
            while(playingState == 2){
                b.updateMousePos(frame);
            }
            while(playingState == 3){
                b.updateMousePos2(frame);
            }
            frame.remove(b);
        }
        else{
            Hangman.cpu.board b = new Hangman.cpu.board();  
            Hangman.cpu u = new Hangman.cpu();
            b.setFocusable(true);
            b.requestFocusInWindow();
            frame.remove(m);
            frame.add(b);
            frame.setVisible(true);
            playingState = 4;
            u.handleClass(frame,b);
            frame.remove(b);
        }
        }
    }
}