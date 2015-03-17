package com.husq.text.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlMain {

	private static String documentName = "e:xml.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1，创建xml文档
		//createXML();
		//addHotel("1", "100", "维也纳酒店", "深圳市宝安区民治", "2015-03-12", "2015-03-13");
		//addHotel("2", "150", "客佳旅馆", "深圳市宝安区民治", "2015-03-12", "2015-03-13");
		
		//2，查询xml节点信息
		//String[] list = queryHotel("1");
		//for(int i=0;i<list.length;i++){
		//	System.out.println(list[i]);
		//}
		
		//3，修改xml信息
		//modifyHotel("1", "100", "维也纳酒店", "深圳市宝安区民治", "2015-03-10", "2015-03-11");
		
		//4，删除节点信息
		//deleteHotel("2");
	}
	
	/*
     * 建立xml文件，并写入根节点
     */ 
    public static void createXML() { 
        // 如果文件存在则不建立xml文件 
        File file = new File(documentName); 
        if (file.exists()) { 
            return; 
        } 
        DocumentFactory factory = new DocumentFactory(); 
        Document document = factory.createDocument(); 
           
        // 建立根节点 
        document.addElement("root"); 
           
        // 建立一个xml文件，将Dom4j树写入xml文件 
        try { 
            FileWriter fw = new FileWriter(file); 
            XMLWriter writer = new XMLWriter(fw); 
            writer.write(document); 
            fw.close();
            
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
    
    /*
     * 新添打折宾馆信息
     */ 
    public static void addHotel(String id, String price, String name, String address,  
            String beginTime, String endTime) { 
        SAXReader reader = new SAXReader(); 
        reader.setStripWhitespaceText(true); 
        try { 
            Document document = reader.read(new File(documentName)); 
   
            // 获取根节点 
            Element root = document.getRootElement(); 
   
            // 添加一个新的hotel节点 
            Element hotel = root.addElement("hotel"); 
   
            // 添加和设置新hotel节点信息 
            hotel.addAttribute("id", id); 
            Element priceElement = hotel.addElement("price"); 
            Element nameElement = hotel.addElement("name"); 
            Element addressElement = hotel.addElement("address"); 
            Element beginTimeElement = hotel.addElement("beginTime"); 
            Element endTimeElement = hotel.addElement("endTime"); 
            priceElement.setText(price); 
            nameElement.setText(name); 
            addressElement.setText(address); 
            beginTimeElement.setText(beginTime); 
            endTimeElement.setText(endTime); 
            FileWriter fw = new FileWriter(documentName); 
            XMLWriter writer = new XMLWriter(fw); 
            writer.write(document); 
            fw.close(); 
        } catch (IOException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        } catch (DocumentException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace(); 
        }  
    } 
   
    /*
     * 根据宾馆呢id，查找宾馆打折信息
     */ 
    public static String[] queryHotel(String id) { 
        SAXReader reader = new SAXReader(); 
        String[] queryResult = new String[6]; 
        int index = 0; 
   
        try { 
            Document document = reader.read(new File(documentName)); 
   
            // 获取根节点 
            Element root = document.getRootElement(); 
   
            // 获取根节点包含的所有子节点 
            List nodes = root.elements(); 
            Element e; 
            List sunNodes; 
            Element sunE; 
            for (Object element : nodes) { 
                e = (Element)element; 
   
                // 如果id值与设定的id值相等，则遍历该节点，获取信息 
                if ((e.attribute("id")).getText().equals(id)) { 
                    // 获取当前子节点的所有子节点 
                    sunNodes = e.elements(); 
                    queryResult[index++] = id; 
                    for (Object sunElement : sunNodes) { 
                        sunE = (Element)sunElement; 
                        queryResult[index++] = sunE.getTextTrim(); 
                    } 
                    break; 
                } 
            } 
        } catch (DocumentException e) { 
            e.printStackTrace(); 
        } 
        return queryResult; 
    } 
   
    /*
     * 根据宾馆id，查找相应宾馆，并修改宾馆信息
     */ 
   public static  void modifyHotel(String id, String price, String name, String address,  
            String beginTime, String endTime) { 
        SAXReader reader = new SAXReader(); 
   
        try { 
            Document document = reader.read(new File(documentName)); 
   
            // 获取根节点 
            Element root = document.getRootElement(); 
   
            // 获取根节点包含的所有子节点 
            List nodes = root.elements(); 
            Element e; 
            List sunNodes; 
            for (Object element : nodes) { 
                e = (Element)element; 
                int index = 0; 
   
                // 如果id值与设定的id值相等，则遍历该节点，获取信息 
                if ((e.attribute("id")).getText().equals(id)) { 
                    // 获取当前子节点的所有子节点 
                    sunNodes = e.elements(); 
                    for (Object sunElement : sunNodes) { 
                        if (index == 0) { 
                            ((Element)sunElement).setText(price); 
                        } 
                        if (index == 1) { 
                            ((Element)sunElement).setText(name); 
                        } 
                        if (index == 2) { 
                            ((Element)sunElement).setText(address); 
                        } 
                        if (index == 3) { 
                            ((Element)sunElement).setText(beginTime); 
                        } 
                        if (index == 4) { 
                            ((Element)sunElement).setText(endTime); 
                        } 
                        index++; 
                    } 
                    break; 
                } 
            } 
            FileWriter fw = new FileWriter(documentName); 
            XMLWriter writer = new XMLWriter(fw); 
            writer.write(document); 
            fw.close(); 
        } catch (DocumentException e) { 
            e.printStackTrace(); 
        } catch (IOException e1) { 
            // TODO Auto-generated catch block 
            e1.printStackTrace(); 
        } 
    } 
   
    /*
     * 根据id，删除指定hotel信息
     */ 
   public static void deleteHotel(String id) { 
        SAXReader reader = new SAXReader(); 
   
        try { 
            Document document = reader.read(new File(documentName)); 
   
            // 获取根节点 
            Element root = document.getRootElement(); 
   
            // 获取根节点包含的所有子节点 
            List nodes = root.elements(); 
            Element e; 
            for (Object element : nodes) { 
                e = (Element)element; 
   
                // 如果id值与设定的id值相等，则遍历该节点，获取信息 
                if ((e.attribute("id")).getText().equals(id)) { 
                    // 删除该节点 
                    root.remove(e); 
                    break; 
                } 
            } 
            FileWriter fw = new FileWriter(documentName); 
            XMLWriter writer = new XMLWriter(fw); 
            writer.write(document); 
            fw.close(); 
        } catch (DocumentException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    }
}
