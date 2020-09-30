//判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
//示例 1:

//输入: 121
//输出: true

class Solution {
    public boolean isPalindrome(int x) {
   //转化为字符数组进行判别
    //    String s=String.valueOf(x);
    //    char []c=new char[50];
     //    char[]c=s.toCharArray();
     //   for(int i=0;i<s.length();i++){
    //        c[i]=s.charAt(i);
     //   }
    //    int i=0;
     //   int j=s.length()-1;
    //        while(i<j){
     //           if(c[i]!=c[j]){
     //               return false;
     //           }
      //          i++;
     //           j--;
     //       }
     //   return true;           
   // }
//}     
        int count=0;
        int []a=new int[50];
        if(x==0)
        return true;
        else if(x%10==0||x<0)
        return false;
        while(x!=0)
        {
         a[count]=x%10;
         x=x/10;
         count=count+1;   
        }
        int i=0;
        int j=count-1;//length属性对于数组，length()对于字符串
        while(i<j){
            if(a[i]!=a[j])
            return false;
            else{
            i++;
            j--;
            }
        }
        return true;
    }
}
