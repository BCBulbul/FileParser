/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereaderbytype;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author burakcan 
 */


public class FileChooser 
{
 private JFileChooser fileChooser;
 private File file;
 private ArrayList<String> dataFromButton;
    
    public  FileChooser()
    {
        createChooser();
        
    }
 
    
    public void createChooser()
    {
        JFileChooser jfc=new JFileChooser();
        int returnVal=jfc.showOpenDialog(null);
        if(returnVal==jfc.APPROVE_OPTION)
        {
            File file=jfc.getSelectedFile();
           this.setFile(file);
            
                            
        }
        else if(returnVal==jfc.CANCEL_OPTION)
        {
            System.exit(0);
        }
        
        
        this.setFileChooser(jfc);
    }

    /**
     * @return the fileChooser
     */
    public JFileChooser getFileChooser() 
    {
        return fileChooser;
    }

    /**
     * @param fileChooser the fileChooser to set
     */
    public void setFileChooser(JFileChooser fileChooser)
    {
        this.fileChooser = fileChooser;
    }

    /**
     * @return the file
     */
    public File getFile() 
    {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) 
    {
        this.file = file;
    }

    /**
     * @return the dataFromButton
     */
    public ArrayList<String> getDataFromButton() 
    {
        return dataFromButton;
    }

    /**
     * @param dataFromButton the dataFromButton to set
     */
    public void setDataFromButton(ArrayList<String> dataFromButton) 
    {
        this.dataFromButton = dataFromButton;
    }
    
    
    
    public String [] getFileType()
    {
        
        String [] arrayToSplit=this.getFile().getName().split("\\.");
        
        return arrayToSplit;
    }
    
    

    
    
  
    
}
