import java.io.File;

public class flymv {

    public static void  main(String[] args){
        String reName = "";
        if(args.length<2) {
            System.out.print("传入参数不够.....");
            return;
        }
        args[1] = args[1].replace("\'","");
        args[1] = args[1].replace("\"","");
        if(args.length>2){
            reName = args[2];
        }
        File file = new File(args[0]);
        File files[] = file.listFiles();
        for(File f:files){
            String flieName = f.getAbsolutePath();
            String path = flieName.substring(0,flieName.lastIndexOf(File.separator));
            String name = flieName.substring(Math.max(0,flieName.lastIndexOf(File.separator)+1));
            String newName = path+File.separator+name.replace(args[1],reName);
            System.out.print(newName+"\n");
            if(!flieName.equals(newName)){
                f.renameTo(new File(newName));
            }
        }
    }
}
