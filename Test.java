import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.*;
import java.lang.String;
public class Test
{
  public static long iter = 0;
  public static void main(String[] args)
  {
    long startTime = System.nanoTime();
    long numT = 0;
    long numI = 0;
    AtomicInteger inside = new AtomicInteger();
    if(args.length!=2)
    {
    System.out.println("Enter two arguments");
    System.exit(1);
    }
    else
    {
      try
      {
        numT = Long.parseLong(args[0]);
        numI = Long.parseLong(args[1]);
      } catch(Exception e)
      {
        System.out.println("Enter two LONG arguments");
        System.exit(1);
      }
    }
    iter = numI / numT;
    Thread t = new Thread(() ->{
      for(int i = 0;i<iter;i++)
      {
      double x = ThreadLocalRandom.current().nextDouble(1);
      double y = ThreadLocalRandom.current().nextDouble(1);
      if(x*x+y*y<1)
      inside.incrementAndGet();
      }
    });
    Thread[] wholethreads = new Thread[(int)numT];
    for(int x=0;x<numT;x++)
    {
      wholethreads[x] = new Thread(t);
    }
    for(int x=0;x<numT;x++)
    {
      wholethreads[x].start();
    }
    try{
    for(int x=0;x<numT;x++)
    {
      wholethreads[x].join();
    }
  }
  catch(Exception e){}
    System.out.println("Total = "+numI);
    System.out.println("Inside = "+inside);
    System.out.println("Ratio = "+((double)inside.longValue()/(double)numI));
    System.out.println("Pi = "+(4.0*inside.longValue()/numI));

    long endTime = System.nanoTime();

    double duration = (endTime - startTime)/1000000000.0;
    System.out.println("\nTime = "+duration);
  }
}
