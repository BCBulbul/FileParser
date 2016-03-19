/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.org.mozilla.javascript.internal.json.JsonParser;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Element;


/**
 *
 * @author Burak Can BÜLBÜL - www.burakcanbulbul.com
 */
public class Parser
{
    FileChooser fileChooser;
    File file;
    BufferedReader bReader;
    Parser fReader;
    ArrayList<String> getParsedData;
    PDFTextStripper pdfStripper;
    PDDocument pdDoc;
    COSDocument cosDoc;
    FileInputStream  inputStream;
    PDFParser parser;
    
    public Parser()
    {
        fileChooser=new FileChooser();
        file=fileChooser.getFile();
        
        
    }
    
    
    public ArrayList<String> fileParser() throws IOException, SAXException
    {
       String path= file.getPath();
       String [] getArray=fileChooser.getFileType();
       String type=getArray[1];
        //System.out.println("Type: "+type);
       String fileName=getArray[0];
       String fileContent="";
       file=fileChooser.getFile();
       getParsedData=new ArrayList<>();
       
       switch (type) 
         {
            case "txt":
                
              try
                  {
               FileReader contentReader=new FileReader(file.getPath());
               bReader=new BufferedReader(contentReader);
               while((fileContent=bReader.readLine())!=null)
                {
                   getParsedData.add(fileContent);
                }
                   }
              
          catch (FileNotFoundException ex)
            {
               Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                break;
                
            case "html":
                // https://scholar.google.com.tr/scholar?hl=tr&q=ecir+u%C4%9Fur+k%C3%BC%C3%A7%C3%BCksille&btnG=&lr=
                String url="";
                try 
                {
                  if(url.isEmpty())
                {
                   
                Document doc = Jsoup.parse(file,null);
                fileContent=doc.text();
                getParsedData.add(fileContent);
                
                        
                }
                else
                {
                  Document doc=Jsoup.connect(url).get();
                  Elements elements=doc.select("div.gs_r");
                  for(Element div:elements)
                  {
                    fileContent+=div.text();
                  }
                  
                   getParsedData.add(fileContent);
                  
                }  
                  
                }
                catch (Exception e) 
                {
                e.printStackTrace();
                }
               
                
                break;
                
            case "pdf":
              try 
              
                {
                    inputStream=new FileInputStream(file);
                    parser=new PDFParser(inputStream);
                    parser.parse();
                    cosDoc=parser.getDocument();
                    pdfStripper=new PDFTextStripper();
                    pdDoc=new PDDocument(cosDoc);
                    pdfStripper.setStartPage(1);
                    pdfStripper.setEndPage(2);
                    fileContent=pdfStripper.getText(pdDoc);
                    getParsedData.add(fileContent); 
                   
                    
                } 
                
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                
                break;
                
            case "doc":          
          try
          {
            
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            fileContent=extractor.getText();
            getParsedData.add(fileContent);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
                
                break;
                
                
            case "xml":
                /*
               
                parsing xml file path
                
                /home/burakcan/Desktop/eurofxref.xml 
                */
               
               try 
                   {
                DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc=dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                Element firstCube=(Element)doc.getElementsByTagName("Cube").item(0);
                Element secondCube=(Element)firstCube.getElementsByTagName("Cube").item(0);
             
                NodeList nList=doc.getElementsByTagName("Cube");
                       for (int i = 0; i < nList.getLength(); i++) 
                       {
                           Node nNode=nList.item(i);
                           Element eElement=(Element)nNode;
                           getParsedData.add(eElement.getAttribute("currency")+" "+eElement.getAttribute("rate"));
                           
                           
                           
                       }
                   
                  }
                 
                 catch (ParserConfigurationException ex) 
                    {
                      Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
               
                break;
        
                
            default:
                
                JOptionPane.showMessageDialog(null, "This program couldn't parse your choice!","Program Error",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
        }
       
         
        
        
       return getParsedData;
        
    }
    
    
   /* 
    public static void main(String[] args) throws IOException, SAXException
    {
       Parser ff=new Parser();
        System.out.println(ff.fileChooser.getFile());
       ArrayList<String> gelen=ff.fileParser();
        System.out.println(gelen);
        
        
    }
    */
    

  
}
