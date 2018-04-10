package dk.itu.mario.engine.level;
import java.util.*;
import dk.itu.mario.MarioInterface.Constraints;
import dk.itu.mario.MarioInterface.GamePlay;
import dk.itu.mario.MarioInterface.LevelInterface;
import dk.itu.mario.engine.sprites.SpriteTemplate;
import dk.itu.mario.engine.sprites.Enemy;
import dk.itu.mario.engine.level.MyDNA;

//Yuen Han Chan
//To Run: java -cp bin dk.itu.mario.engine.PlayCustomized [PLAYER_TYPE]
public class MyLevel extends Level{
	//Store information about the level
	public   int ENEMIES = 0; //the number of enemies the level contains
	public   int BLOCKS_EMPTY = 0; // the number of empty blocks
	public   int BLOCKS_COINS = 0; // the number of coin blocks
	public   int BLOCKS_POWER = 0; // the number of power blocks
	public   int COINS = 0; //These are the coins in boxes that Mario collect
 
	private Random random = new Random(1);
	
	private static final int STARTOFFSET = 5;
	private static final int EXITOFFSET = 5;
	private static final int EXITPOSITION = 3; // number of blocks from the end
	private static final int DEFAULTHEIGHT = 15;
	
	public MyLevel(int width, int height)
    {
		super(width, height);
    }

	public MyLevel(MyDNA dna, int type)
	{
		// Add 10 units for the beginning platform and exit platform
		this(205, 15);
		create(dna, type);
	}
	
	public void create(MyDNA dna, int type)
	{
		//Initial starting platform is 3 blocks wide and flat ground.
		for (int x = 0; x < STARTOFFSET; x++)
		{
			this.setBlock(x, DEFAULTHEIGHT-2, HILL_TOP);
			this.setBlock(x, DEFAULTHEIGHT-1, GROUND);
		}

		// Start your level at block index STARTOFFSET.
		//// YOUR CODE GOES BELOW HERE ////
        String currentDNA = dna.getChromosome();   // C1E4S4J5
        int totalLenghtLeft = (width-EXITOFFSET)-STARTOFFSET;
        int currentLenght = STARTOFFSET;
        int currentIndex = 0;
        int fixLenght = 6;
        // int coinAmount = currentDNA.charAt(1);
        // int enemyAmount = currentDNA.charAt(3);
        // int jumpAmount = currentDNA.charAt(5);

        // String genotype = dna.getGenotype();
        // System.out.println("genotype: " + genotype);
        // for (int x = STARTOFFSET; x < width-EXITOFFSET; x++)
        // for (int x = STARTOFFSET; x < STARTOFFSET+1; x++)
        // {
        //     // buildJumpWithLenght(x, 10);
        //     buildHillStraight(currentLenght,fixLenght,10);
        //     // this.setBlock(x, DEFAULTHEIGHT-2, HILL_TOP);
        //     // this.setBlock(x, DEFAULTHEIGHT-1, GROUND);
        //     fixWalls();
        //     // this.setBlock(x, DEFAULTHEIGHT, COINS)
        //     // this.setBlock(x, DEFAULTHEIGHT-1, GROUND);
        // }

        int usedLenght = 0;
        Random rand = new Random();
        while(usedLenght<totalLenghtLeft){
            int types = currentDNA.charAt(currentIndex);
            // int types = 0;
            int llUsed = 0;
            if(types=='0'){ //Coins
                llUsed = buildCoinRoad(currentLenght, fixLenght,false);
                fixWalls();
            }
            if(types=='1'){ //Jumps
                // usedLenght = buildCoinRoad(currentLenght, fixLenght);
                // fixWalls();
                // llUsed = buildJump(currentLenght, 15);
                // buildJumpMe(currentLenght, fixLenght);
                // usedLenght = buildCoinRoad(currentLenght, fixLenght);
                llUsed = JumpRockOne(currentLenght,15,false);
                fixWalls();
            }
            if(types=='2'){ //Enemy
            // else{
                // int levelType = rand.nextInt(3);
                // if(levelType%2==0){
                llUsed = buildHillStraight(currentLenght, 10, 20);
                // llUsed = JumpRockOne(currentLenght,15,true);
                fixWalls();

                // usedLenght = buildCoinRoad(currentLenght, fixLenght);
                // fixWalls();
            }
            if(types=='3'){ //Enemy and coin
                llUsed = buildCoinRoad(currentLenght, fixLenght,true);
                // buildCoinRoad(currentLenght, fixLenght/2);
                // buildHillStraight(currentLenght+(fixLenght/2), fixLenght/2, 5);
                fixWalls();
                // usedLenght = buildCoinRoad(currentLenght, fixLenght);
                // fixWalls();
            }    
            usedLenght+=(llUsed+2);        
            currentLenght += (llUsed+2);
            currentIndex+=2;
        }
        // System.out.println("finalIndexUsed: "+currentIndex);
		
		//// YOUR CODE GOES ABOVE HERE ////

		// Final exit is on flat ground in the last 3 blocks.
		for (int x = width-EXITOFFSET; x < width; x++)
		{
			this.setBlock(x, DEFAULTHEIGHT-2, HILL_TOP);
			this.setBlock(x, DEFAULTHEIGHT-1, GROUND);
		}

		xExit = width-EXITPOSITION;
		yExit = DEFAULTHEIGHT-2;

	}

    public int JumpRockOne(int x0, int maxLength, boolean enemy){
        int length = maxLength;
        Random rand = new Random();
        // rand.setSeed(1);
        int floor = height - 3 - random.nextInt(4);
        int marioFloor = height-2;
        // leave gap at the end
        // setBlock(x0,marioFloor, HILL_TOP_LEFT);
        // for(int i = 0; i<3;i++){
        //     setBlock(x0,marioFloor+i, LEFT_GRASS_EDGE);
        // }
        int blockBegin = 0;
        for(int x=x0+1; x<(length+x0); x++){
            for (int y = 0; y < height; y++){
                // all have to be cover with hillTop
                // setBlock(x,marioFloor, HILL_TOP);
                setBlock(x,marioFloor+1, GROUND);
                setBlock(x,marioFloor+2, GROUND);

                // randomBlockLengh
                int stairLenght = rand.nextInt(4)+1;
                // stairLenght = 10;
                // blocks before stairBegin
                int beginBlock = rand.nextInt(3)+2;
                if((x>(beginBlock+x0)) && (x<(beginBlock+x0+stairLenght))){
                    // int yAmount = 1;
                    for(int i = 0; i<(x-x0-beginBlock); i ++){
                        setBlock(x,marioFloor-(x-x0-beginBlock),ROCK); 
                    }
                }
                // System.out.println("stair lenght: " + stairLenght);
            }
            blockBegin = rand.nextInt(3);
            if(x>(x0+blockBegin) && (x<(x0+blockBegin+4))) {
                setBlock(x,marioFloor-5, BLOCK_EMPTY);
                setBlock(x,marioFloor-5-4, BLOCK_COIN);
                if(enemy){
                    addEnemyLine(x, x+4, marioFloor-6, rand.nextInt(40));
                }
            }
            if(x>(x0+blockBegin+3) && (x<(x0+blockBegin+4+3))) {
                // boolean powerUP = false;
                if(rand.nextInt(6)%4==0){
                    setBlock(x,marioFloor-5-2, BLOCK_POWERUP);
                }else{
                    setBlock(x,marioFloor-5-2, BLOCK_EMPTY);
                }
            }

        }
        if(enemy){
            addEnemyLine(x0, x0+rand.nextInt(5), marioFloor-1, rand.nextInt(40));
            addEnemyLine(x0+blockBegin+3, x0+blockBegin+3+rand.nextInt(3), marioFloor-5-3, rand.nextInt(40));
        }
        // setBlock(x0+length,marioFloor-1, HILL_TOP_RIGHT);
        return length;
    }

    public int buildJumpMe(int x, int desireLength){
        int floor = height;
        System.out.println("floor: " + floor);
        for (int i = x ; i<(x+desireLength); i++){
            // setBlock(i,10, CANNON_TOP);
            setBlock(i,11, CANNON_MID);
            setBlock(i,12,CANNON_BASE);
            setBlock(i,13, HILL_TOP);
            // for (int y = 0; y<10; y++){
            //     if(y>=floor){
            //         setBlock(x,y,GROUND);
            //     }
            // }
        }
        return desireLength;
    }
	/* BELOW HERE ARE EXAMPLE FUNCTIONS FOR HOW TO CREATE SOME INTERESTING STRUCTURES */

    public int buildCoinRoad(int xo, int desireLength, boolean enemy)
    {   
        //jl: jump length
        //js: the number of blocks that are available at either side for free
        int js = random.nextInt(4) + 2;
        int jl = random.nextInt(2) + 2;
        // int length = js * 2 + jl;
        int length = desireLength;

        boolean hasStairs = random.nextInt(3) == 0;
        hasStairs = true;
        Random rand = new Random();
        int floor = height - 1 - random.nextInt(4);
        //run from the start x position, for the whole length
        for (int x = xo; x < xo + length; x++)
        {
            // if (x < xo + js || x > xo + length - js - 1)
            // {
                //run for all y's since we need to paint blocks upward
                for (int y = 0; y < height; y++)
                {   //paint ground up until the floor
                    if (y >= floor)
                    {
                        setBlock(x, y, GROUND);
                        setBlock(x, y-1, COIN);
                    }
                  //if it is above ground, start making stairs of rocks
                    else if (hasStairs)
                    {   //LEFT SIDE
                        if (x < xo + js)
                        { //we need to max it out and level because it wont
                          //paint ground correctly unless two bricks are side by side
                            if (y >= floor - (x - xo) + 1)
                            {
                                // setBlock(x, y, ROCK);
                                setBlock(x, y-1, COIN);
                            }
                            if (enemy){
                                addEnemyLine(x, (xo+js), floor, rand.nextInt(35));
                            }
                        }
                        else
                        { //RIGHT SIDE
                            if (y >= floor - ((xo + length) - x) + 2)
                            {
                                // setBlock(x, y, ROCK);
                                setBlock(x, y-1, COIN);
                            }
                        }
                    }
                }
            // }
        }

        return length;
    }

    //A built in function for helping to build a jump
    public int buildJump(int xo, int maxLength)
    {	
    	//jl: jump length
    	//js: the number of blocks that are available at either side for free
        int js = random.nextInt(4) + 2;
        int jl = random.nextInt(2) + 2;
        int length = maxLength;

        boolean hasStairs = random.nextInt(3) == 0;

        int floor = height - 1 - random.nextInt(4);
		//run from the start x position, for the whole length
        for (int x = xo; x < xo + length; x++)
        {
            // if (x < xo + js || x > xo + length - js - 1)
            // {
            	//run for all y's since we need to paint blocks upward
                for (int y = 0; y < height; y++)
                {	//paint ground up until the floor
                    if (y >= floor)
                    {
                        setBlock(x, y, GROUND);
                    }
                  //if it is above ground, start making stairs of rocks
                    else if (hasStairs)
                    {	//LEFT SIDE
                        if (x < xo + js)
                        { //we need to max it out and level because it wont
                          //paint ground correctly unless two bricks are side by side
                            if (y >= floor - (x - xo) + 1)
                            {
                                setBlock(x, y, ROCK);
                            }
                        }
                        else
                        { //RIGHT SIDE
                            if (y >= floor - ((xo + length) - x) + 2)
                            {
                                setBlock(x, y, ROCK);
                            }
                        }
                    }
                }
            // }
        }

        return length;
    }

   	//A built in function for helping to build a cannon
    public int buildCannons(int xo, int maxLength)
    {
        int length = random.nextInt(10) + 2;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        int xCannon = xo + 1 + random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xCannon)
            {
                xCannon += 2 + random.nextInt(4);
            }
            if (xCannon == xo + length - 1) xCannon += 10;
            int cannonHeight = floor - random.nextInt(4) - 1;

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    setBlock(x, y, GROUND);
                }
                else
                {
                    if (x == xCannon && y >= cannonHeight)
                    {
                        if (y == cannonHeight)
                        {
                            setBlock(x, y, Level.CANNON_TOP);
                        }
                        else if (y == cannonHeight + 1)
                        {
                            setBlock(x, y, Level.CANNON_MID);
                        }
                        else
                        {
                            setBlock(x, y, Level.CANNON_BASE);
                        }
                    }
                }
            }
        }

        return length;
    }

    //A built in function for building a flat hill
    public int buildHillStraight(int xo, int maxLength, int difficulty)
    {
        // int length = random.nextInt(10) + 10;
        // if (length > maxLength) length = maxLength;
        int length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    setBlock(x, y, GROUND);
                }
            }
        }

        addEnemyLine(xo + 1, xo + length - 1, floor - 1, difficulty);

        int h = floor;

        boolean keepGoing = true;

        boolean[] occupied = new boolean[length];
        while (keepGoing)
        {
            h = h - 2 - random.nextInt(3);

            if (h <= 0)
            {
                keepGoing = false;
            }
            else
            {
                int l = random.nextInt(5) + 3;
                int xxo = random.nextInt(length - l - 2) + xo + 1;

                if (occupied[xxo - xo] || occupied[xxo - xo + l] || occupied[xxo - xo - 1] || occupied[xxo - xo + l + 1])
                {
                    keepGoing = false;
                }
                else
                {
                    occupied[xxo - xo] = true;
                    occupied[xxo - xo + l] = true;
                    addEnemyLine(xxo, xxo + l, h - 1, difficulty);
                    if (random.nextInt(4) == 0)
                    {
                        decorate(xxo - 1, xxo + l + 1, h, difficulty);
                        keepGoing = false;
                    }
                    for (int x = xxo; x < xxo + l; x++)
                    {
                        for (int y = h; y < floor; y++)
                        {
                            int xx = 5;
                            if (x == xxo) xx = 4;
                            if (x == xxo + l - 1) xx = 6;
                            int yy = 9;
                            if (y == h) yy = 8;

                            if (getBlock(x, y) == 0)
                            {
                                setBlock(x, y, (byte) (xx + yy * 16));
                            }
                            else
                            {
                                if (getBlock(x, y) == HILL_TOP_LEFT) setBlock(x, y, HILL_TOP_LEFT_IN);
                                if (getBlock(x, y) == HILL_TOP_RIGHT) setBlock(x, y, HILL_TOP_RIGHT_IN);
                            }
                        }
                    }
                }
            }
        }

        return length;
    }

    //A built in function for adding a line of enemies at a given height (y) and difficulty
    public void addEnemyLine(int x0, int x1, int y, int difficulty)
    {
        for (int x = x0; x < x1; x++)
        {
            if (random.nextInt(35) < difficulty + 1)
            {
                int type = random.nextInt(4);

                if (difficulty < 1)
                {
                    type = Enemy.ENEMY_GOOMBA;
                }
                else if (difficulty < 3)
                {
                    type = random.nextInt(3);
                }
                setSpriteTemplate(x, y, new SpriteTemplate(type,false));
                // setSpriteTemplate(x, y, new SpriteTemplate(type, random.nextInt(35) < difficulty));//Second boolean value determines if enemy is flying
                ENEMIES++;
            }
        }
    }

    //A built in function for building a tube (difficulty determines chance of flower spawn)
    public int buildTubes(int xo, int maxLength, int difficulty)
    {
        int length = random.nextInt(10) + 5;
        if (length > maxLength) length = maxLength;

        int floor = height - 1 - random.nextInt(4);
        int xTube = xo + 1 + random.nextInt(4);
        int tubeHeight = floor - random.nextInt(2) - 2;
        for (int x = xo; x < xo + length; x++)
        {
            if (x > xTube + 1)
            {
                xTube += 3 + random.nextInt(4);
                tubeHeight = floor - random.nextInt(2) - 2;
            }
            if (xTube >= xo + length - 2) xTube += 10;

            if (x == xTube && random.nextInt(11) < difficulty + 1)
            {
                setSpriteTemplate(x, tubeHeight, new SpriteTemplate(Enemy.ENEMY_FLOWER, false));
                ENEMIES++;
            }

            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    setBlock(x, y,GROUND);

                }
                else
                {
                    if ((x == xTube || x == xTube + 1) && y >= tubeHeight)
                    {
                        int xPic = 10 + x - xTube;

                        if (y == tubeHeight)
                        {
                        	//tube top
                            setBlock(x, y, (byte) (xPic + 0 * 16));
                        }
                        else
                        {
                        	//tube side
                            setBlock(x, y, (byte) (xPic + 1 * 16));
                        }
                    }
                }
            }
        }

        return length;
    }
	

    //A built in function for building a straight path
    public int buildStraight(int xo, int maxLength, boolean safe, int difficulty)
    {
        int length = random.nextInt(10) + 2;

        if (safe)
        	length = 10 + random.nextInt(5);

        if (length > maxLength)
        	length = maxLength;

        int floor = height - 1 - random.nextInt(4);

        //runs from the specified x position to the length of the segment
        for (int x = xo; x < xo + length; x++)
        {
            for (int y = 0; y < height; y++)
            {
                if (y >= floor)
                {
                    setBlock(x, y, GROUND);
                }
            }
        }

        if (!safe)
        {
            if (length > 5)
            {
                decorate(xo, xo + length, floor, difficulty);
            }
        }

        return length;
    }

    //A built in function to add "decoration"
    public void decorate(int xStart, int xLength, int floor, int difficulty)
    {
    	//if its at the very top, just return
        if (floor < 1)
        	return;

        //        boolean coins = random.nextInt(3) == 0;
        boolean rocks = true;

        //add an enemy line above the box
        addEnemyLine(xStart + 1, xLength - 1, floor - 1, difficulty);

        int s = random.nextInt(4);
        int e = random.nextInt(4);

        if (floor - 2 > 0){
            if ((xLength - 1 - e) - (xStart + 1 + s) > 1){
                for(int x = xStart + 1 + s; x < xLength - 1 - e; x++){
                    setBlock(x, floor - 2, COIN);
                    COINS++;
                }
            }
        }

        s = random.nextInt(4);
        e = random.nextInt(4);
        
        //this fills the set of blocks and the hidden objects inside them
        if (floor - 4 > 0)
        {
            if ((xLength - 1 - e) - (xStart + 1 + s) > 2)
            {
                for (int x = xStart + 1 + s; x < xLength - 1 - e; x++)
                {
                    if (rocks)
                    {
                        if (x != xStart + 1 && x != xLength - 2 && random.nextInt(3) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                setBlock(x, floor - 4, BLOCK_POWERUP);
                                BLOCKS_POWER++;
                            }
                            else
                            {	//the fills a block with a hidden coin
                                setBlock(x, floor - 4, BLOCK_COIN);
                                BLOCKS_COINS++;
                            }
                        }
                        else if (random.nextInt(4) == 0)
                        {
                            if (random.nextInt(4) == 0)
                            {
                                setBlock(x, floor - 4, (byte) (2 + 1 * 16));
                            }
                            else
                            {
                                setBlock(x, floor - 4, (byte) (1 + 1 * 16));
                            }
                        }
                        else
                        {
                            setBlock(x, floor - 4, BLOCK_EMPTY);
                            BLOCKS_EMPTY++;
                        }
                    }
                }
            }
        }
    }

    //A built in function to fix edges or walls at the end of level creation
    public void fixWalls()
    {
        boolean[][] blockMap = new boolean[width + 1][height + 1];

        for (int x = 0; x < width + 1; x++)
        {
            for (int y = 0; y < height + 1; y++)
            {
                int blocks = 0;
                for (int xx = x - 1; xx < x + 1; xx++)
                {
                    for (int yy = y - 1; yy < y + 1; yy++)
                    {
                        if (getBlockCapped(xx, yy) == GROUND){
                        	blocks++;
                        }
                    }
                }
                blockMap[x][y] = blocks == 4;
            }
        }
        blockify(this, blockMap, width + 1, height + 1);
    }

    //blockify is used for fixing up walls
    public void blockify(Level level, boolean[][] blocks, int width, int height){
        int to = 0;

        boolean[][] b = new boolean[2][2];

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                for (int xx = x; xx <= x + 1; xx++)
                {
                    for (int yy = y; yy <= y + 1; yy++)
                    {
                        int _xx = xx;
                        int _yy = yy;
                        if (_xx < 0) _xx = 0;
                        if (_yy < 0) _yy = 0;
                        if (_xx > width - 1) _xx = width - 1;
                        if (_yy > height - 1) _yy = height - 1;
                        b[xx - x][yy - y] = blocks[_xx][_yy];
                    }
                }

                if (b[0][0] == b[1][0] && b[0][1] == b[1][1])
                {
                    if (b[0][0] == b[0][1])
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
                        }
                        else
                        {
                            // KEEP OLD BLOCK!
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                        	//down grass top?
                            level.setBlock(x, y, (byte) (1 + 10 * 16 + to));
                        }
                        else
                        {
                        	//up grass top
                            level.setBlock(x, y, (byte) (1 + 8 * 16 + to));
                        }
                    }
                }
                else if (b[0][0] == b[0][1] && b[1][0] == b[1][1])
                {
                    if (b[0][0])
                    {
                    	//right grass top
                        level.setBlock(x, y, (byte) (2 + 9 * 16 + to));
                    }
                    else
                    {
                    	//left grass top
                        level.setBlock(x, y, (byte) (0 + 9 * 16 + to));
                    }
                }
                else if (b[0][0] == b[1][1] && b[0][1] == b[1][0])
                {
                    level.setBlock(x, y, (byte) (1 + 9 * 16 + to));
                }
                else if (b[0][0] == b[1][0])
                {
                    if (b[0][0])
                    {
                        if (b[0][1])
                        {
                            level.setBlock(x, y, (byte) (3 + 10 * 16 + to));
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (3 + 11 * 16 + to));
                        }
                    }
                    else
                    {
                        if (b[0][1])
                        {
                        	//right up grass top
                            level.setBlock(x, y, (byte) (2 + 8 * 16 + to));
                        }
                        else
                        {
                        	//left up grass top
                            level.setBlock(x, y, (byte) (0 + 8 * 16 + to));
                        }
                    }
                }
                else if (b[0][1] == b[1][1])
                {
                    if (b[0][1])
                    {
                        if (b[0][0])
                        {
                        	//left pocket grass
                            level.setBlock(x, y, (byte) (3 + 9 * 16 + to));
                        }
                        else
                        {
                        	//right pocket grass
                            level.setBlock(x, y, (byte) (3 + 8 * 16 + to));
                        }
                    }
                    else
                    {
                        if (b[0][0])
                        {
                            level.setBlock(x, y, (byte) (2 + 10 * 16 + to));
                        }
                        else
                        {
                            level.setBlock(x, y, (byte) (0 + 10 * 16 + to));
                        }
                    }
                }
                else
                {
                    level.setBlock(x, y, (byte) (0 + 1 * 16 + to));
                }
            }
        }
    }
    
    //Clones this level
    public MyLevel clone(){

    	MyLevel clone=new MyLevel(width, height);

    	clone.xExit = xExit;
    	clone.yExit = yExit;
    	byte[][] map = getMap();
    	SpriteTemplate[][] st = getSpriteTemplate();
    	
    	for (int i = 0; i < map.length; i++)
    		for (int j = 0; j < map[i].length; j++) {
    			clone.setBlock(i, j, map[i][j]);
    			clone.setSpriteTemplate(i, j, st[i][j]);
    	}
    	clone.BLOCKS_COINS = BLOCKS_COINS;
    	clone.BLOCKS_EMPTY = BLOCKS_EMPTY;
    	clone.BLOCKS_POWER = BLOCKS_POWER;
    	clone.ENEMIES = ENEMIES;
    	clone.COINS = COINS;
    	
        return clone;

    }


}
