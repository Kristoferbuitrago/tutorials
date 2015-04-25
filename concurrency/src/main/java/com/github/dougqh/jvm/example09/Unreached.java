package com.github.dougqh.jvm.example09;

import com.github.dougqh.jvm.support.RunWith;
import com.github.dougqh.jvm.support.Output;

@RunWith({"-XX:-TieredCompilation", "-XX:+PrintCompilation"})
@Output(highlight={"Unreached::hotMethod", "made not entrant"})
public class Unreached {
  public static volatile Object thing = null;

  public static void main(final String[] args) throws Exception {
    for ( int i = 0; i < 20_000; ++i ) {
      hotMethod();
    }
   
    Thread.sleep(5_000);
    thing = new Object();

    for ( int i = 0; i < 20_000; ++i ) {
      hotMethod();
    }
    Thread.sleep(5_000);
  }

  static final void hotMethod() {
    if ( thing == null )
      System.out.print("");
    else
      System.out.print("");
  }
}