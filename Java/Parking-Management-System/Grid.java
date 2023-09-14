import java.util.*;
import java.text.ParseException;




class Grid {

    static void initiateGrid( int [][]plot)
    {
        for( int [] it : plot)
        {
            for(int i:it)
            {
                i = 1;
            }
        }
    }

    static void checkGrid( int [][]plot)
    {
        for( int [] it : plot)
        {
            for(int i:it)
            {
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    static int findGrid( int [][]plot)
    {
        for(int i = 0; i<3; i++)
        {
            for(int j=0; j<3; j++)
            {
                if( plot[i][j] == 0)
                {
                    plot[i][j] = 1;
                    return i*10+j;
                }
            }
        }
        return -1;
    }

    static void updateGrid( int plotno, int[][]plot)
    {
        if(plotno/10==0)
        {
            plot[0][plotno] = 0;
        }
        else
        {
            plot[plotno/10][plotno%10] = 0;
        }
    }


    //-----------------------------------------------//
    static void initiateQueue( Queue<Integer> incoming)
    {
        for(int i = 0; i<3; i++)
        {
            incoming.add((int)generateVehicle());
        }
    }

    static void fillQueue( Queue<Integer> incoming)
    {
            incoming.add((int)generateVehicle());
    }

    static void checkQueue( Queue<Integer> incoming)
    {
        for( Integer it : incoming)
        {
            System.out.print(it +" ");
        }
        System.out.println();
    }

    //------------------------------------------------//
    static double generateVehicle( )
    {
        double rand = Math.floor(Math.random()*91 + 10);
        return rand;
    }

    //------------------------------------------------//
    static void initiateMap(HashMap<Integer,Character> gridMap){
        Character A = 'A';
        for(Integer i = 0; i<3 ; i++)
        {
            for (Integer j = 0; j<3; j++)
            {
                gridMap.put(i*10+j,A);
                A++;
            }
        }
    }

    static void checkMap(HashMap<Integer,Character> gridMap){
        for(Integer i = 0; i<3 ; i++)
        {
            for (Integer j = 0; j<3; j++)
            {
                System.out.print(gridMap.get(i*10+j)+" ");
            }
            System.out.println();
        }
    }

    //-----------------------------------------------//
    static char findVehicle(int tempno, ArrayList<String> occupant)
    {
        String num = Integer.toString(tempno);
        for(int i=0; i<occupant.size(); i++)
        {
            String temp = occupant.get(i);
            String tempshort = temp.substring(1);
            
            if( tempshort.equals(num))
            {
                return temp.charAt(0);
            }
        }
        return '-';
    }

    static int decode_block(char vehTag, HashMap<Integer,Character> gridMap)
    {
        for (Map.Entry<Integer,Character> entry:gridMap.entrySet())
        {
            if(entry.getValue() == vehTag)
            {
                int key = entry.getKey();
                return key;
            }
        }
        return -1;
    }

    //----------------------------------------------//
    static void checkArray(ArrayList<String> occupant){
        for( String x : occupant)
        {
            System.out.print(x+" ");
        }
        System.out.println();
    }
    static void updateArray(char vehTag,ArrayList<String> occupant){
        for(int i=0; i<occupant.size(); i++)
        {
            if(occupant.get(i).charAt(0)== vehTag)
            {
                occupant.remove(i);
            }
        }
    }

    //---------------------------------------------//
    static void generateBill(int block, int tempNo, char vehTag)
    {
        System.out.println("******* Parking Bill *******");
        System.out.println(" -> Parked at: "+ vehTag);
        System.out.println(" -> Vehical Tag: "+ vehTag+tempNo);
        System.out.println(" -> Total Cost: "+ 100);
    }



    public static void main(String[] args) throws ParseException {
        {
            /* Parking Grid Creation
            - Every Filled spot will be 1
            - Every Empty Spot will be 0
            - the incoming vehicle list will be maintained in a queue
             */
            //global elements
            int [][]plot = new int[3][3];
            HashMap<Integer,Character> gridMap = new HashMap<>();
            Queue<Integer> incoming = new LinkedList<>();
            ArrayList<String> occupant = new ArrayList<>();

            initiateGrid(plot);
//            checkGrid(plot);

            initiateQueue(incoming);
            fillQueue(incoming);
//            checkQueue(incoming);

            initiateMap(gridMap);
//            checkMap(gridMap);

//            checkArray(occupant);

            //--------------------When Vehicle Enters------------------------//
            Scanner sc = new Scanner(System.in);

            System.out.println("Press 's' to start parking");

            Character input = 'N';

            try
            {
                input = sc.next().charAt(0);
            }
            catch (Exception e)
            {
                System.out.println(e);
                System.out.println("Enter Correct input");
            }

            if(input == 's'){

                while(!incoming.isEmpty())
                {
                    String vehicle = incoming.remove().toString();
                    int place = findGrid(plot);
                    String alpha = "";
                    String vehTag = "";

                    if(place!=-1)
                    {
                        if(gridMap.get(place)!=null)
                        {
                            alpha = gridMap.get(place).toString();
                        }
                        vehTag = alpha+vehicle;
                        occupant.add(vehTag);
                    }

                }
            }

            System.out.println("For Operator");
            checkArray(occupant);
            System.out.println();


            System.out.println(
                    "\n type 0  to find you car \n type 1 to generate bill and exit vehicle"
            );
            System.out.println(" type 2 to check grid \n type 3 to check queue");
            System.out.println(" type -1 to exit interface");

            int user = sc.nextInt();

            while(user!=-1)
            {
                switch (user) {
                    case 0: {
                        System.out.println("enter the last two digit of your vehicle");
                        int tempno = sc.nextInt();
                        char vehTag = findVehicle(tempno, occupant);

                        if (vehTag != '-') {
                            int Block = decode_block(vehTag, gridMap);
                            System.out.println("Your Vehicle is at block " + Block + " that is Parking Lot " + vehTag);
                        } else {
                            System.out.println("vehicle not present");
                        }
                        break;
                    }

                    case 1:
                    {
                        System.out.println("Enter your vehicle's Parking Lot no");
                        int block = sc.nextInt();

                        System.out.println("enter the last two digit of your vehicle");
                        int tempno = sc.nextInt();


                        char vehTag = findVehicle(tempno, occupant);

                        updateGrid(block,plot);
                        updateArray(vehTag,occupant);
                        generateBill(block,tempno,vehTag);
                        break;
                    }
                    case 2:
                    {
                        checkGrid(plot);
                        break;
                    }
                    case 3:
                    {
                        checkArray(occupant);
                        break;
                    }
                }
                System.out.println(
                        "\n type 0  to find you car \n type 1 to generate bill and exit vehicle "
                );
                System.out.println(" type 2 to check grid \n type 3 to check queue");
                System.out.println(" type -1 to exit interface");
                user = sc.nextInt();
            }
        }

    }
}
