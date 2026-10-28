package com.shopvote;
import java.io.*;import java.nio.file.*;import java.util.*;
public final class ShopVoteStore {
 private static final ShopVoteStore I=new ShopVoteStore(); private final Properties p=new Properties(); private final Path file=Path.of(System.getProperty("user.home"),".shopvote","data.properties");
 public static ShopVoteStore get(){return I;} private ShopVoteStore(){try{Files.createDirectories(file.getParent());if(Files.exists(file))try(InputStream in=Files.newInputStream(file)){p.load(in);}}catch(IOException e){throw new IllegalStateException("Cannot load local data",e);}}
 private void save(){try{Files.createDirectories(file.getParent());Path t=file.resolveSibling("data.tmp");try(OutputStream o=Files.newOutputStream(t)){p.store(o,"ShopVote local data");}try{Files.move(t,file,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE);}catch(IOException e){Files.move(t,file,StandardCopyOption.REPLACE_EXISTING);}}catch(IOException e){throw new IllegalStateException("Cannot save local data",e);}}
 public synchronized boolean register(String n,String e,String h){if(p.containsKey("user."+e+".hash"))return false;p.setProperty("user."+e+".hash",h);p.setProperty("user."+e+".name",n);save();return true;}
 public synchronized boolean login(String e,String pw){String h=p.getProperty("user."+e+".hash");return h!=null&&PasswordUtil.verify(pw,h);}
 public synchronized boolean voted(String e){return p.containsKey("vote.1."+e);}
 public synchronized boolean vote(String e,Map<Integer,String>a){if(voted(e)||a.size()!=4)return false;for(int q=1;q<=4;q++)if(!options(q).contains(a.get(q)))return false;for(int q=1;q<=4;q++)p.setProperty("vote."+q+"."+e,a.get(q));save();return true;}
 public synchronized Map<String,Integer> counts(int q){Map<String,Integer> m=new LinkedHashMap<>();for(String s:options(q))m.put(s,0);String pre="vote."+q+".";for(String k:p.stringPropertyNames())if(k.startsWith(pre)){String v=p.getProperty(k);if(m.containsKey(v))m.put(v,m.get(v)+1);}return m;}
 public synchronized int voters(){int n=0;for(String k:p.stringPropertyNames())if(k.startsWith("user.")&&k.endsWith(".hash"))n++;return n;}
 public synchronized int total(int q){int n=0;String pre="vote."+q+".";for(String k:p.stringPropertyNames())if(k.startsWith(pre))n++;return n;}
 public static String email(String e){return e==null?"":e.trim().toLowerCase(Locale.ROOT);}
 public static List<String> options(int q){return switch(q){case 1->List.of("Instagram","YouTube","Snapchat","Facebook","Other");case 2->List.of("Amazon","Flipkart","Myntra","Meesho","Other");case 3->List.of("BGMI","Free Fire","Minecraft","GTA V","Other");default->List.of("Python","Java","C++","JavaScript","Other");};}
 public static String question(int q){return switch(q){case 1->"What is your favourite social media app?";case 2->"What is your favourite shopping app?";case 3->"What is your favourite game?";default->"What is your favourite programming language?";};}
}