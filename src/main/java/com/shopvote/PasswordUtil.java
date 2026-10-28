package com.shopvote;
import java.security.*; import java.util.Base64; import javax.crypto.*; import javax.crypto.spec.PBEKeySpec;
public final class PasswordUtil {
 private static final SecureRandom R=new SecureRandom(); private PasswordUtil(){}
 public static String hash(String p){byte[] s=new byte[16];R.nextBytes(s);return "120000:"+Base64.getEncoder().encodeToString(s)+":"+Base64.getEncoder().encodeToString(derive(p.toCharArray(),s,120000));}
 public static boolean verify(String p,String h){try{String[] a=h.split(":");byte[] x=derive(p.toCharArray(),Base64.getDecoder().decode(a[1]),Integer.parseInt(a[0]));byte[] y=Base64.getDecoder().decode(a[2]);if(x.length!=y.length)return false;int d=0;for(int i=0;i<x.length;i++)d|=x[i]^y[i];return d==0;}catch(Exception e){return false;}}
 private static byte[] derive(char[] p,byte[] s,int n){PBEKeySpec spec=new PBEKeySpec(p,s,n,256);try{return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}catch(GeneralSecurityException e){throw new IllegalStateException(e);}finally{spec.clearPassword();}}
}