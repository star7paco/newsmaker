package com.s7soft.gae.news.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.s7soft.gae.news.domain.CategoryClass;
import com.s7soft.gae.news.domain.TargetClass;

public class RssReader {

	public static List<TargetClass> readRss(CategoryClass category) {
		return parseXML(category.getRssUrl());
	}

	private static List<TargetClass> parseXML(String path) {
		List<TargetClass> list = new ArrayList<TargetClass>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();


	        //----------------------------------------------------------------
	        // URLを指定してレスポンスのInputStreamを取得
	        //----------------------------------------------------------------
	        URL url = new URL( path );
	        HttpURLConnection conn = ( HttpURLConnection ) url.openConnection();
	        // ユーザ・エージェントの設定
	        String userAgent = sha1Digest( new Date().toString() + "128-256-384-512-640-768" );
	        if ( ( url.hashCode() + userAgent.hashCode() ) % 2 == 0 ) {
	            userAgent = userAgent.toUpperCase();
	        }
	        conn.setRequestProperty( "User-Agent", userAgent );

	        InputStream is = conn.getInputStream();



			Document document = builder.parse( is );
			Element root = document.getDocumentElement();

			/* Get and print Title of RSS Feed. */
//			NodeList channel = root.getElementsByTagName("channel");
			// NodeList nodeTitle =
			// ((Element)channel.item(0)).getElementsByTagName("title");
			// System.out.println("\nTitle: " +
			// nodeTitle.item(0).getFirstChild().getNodeValue() + "\n");

			/* Get Node list of RSS items */
			NodeList item_list = root.getElementsByTagName("item");
			for (int i = 0; i < item_list.getLength(); i++) {

				TargetClass target = new TargetClass();

				Element element = (Element) item_list.item(i);
				NodeList item_title = element.getElementsByTagName("title");
				NodeList item_link = element.getElementsByTagName("link");

				NodeList item_description = null;
				try{
					item_description = element.getElementsByTagName("description");
				}catch(Exception e) {
				}

				String title = item_title.item(0).getFirstChild()
						.getNodeValue();
				String link = item_link.item(0).getFirstChild().getNodeValue();

				try{
					if(item_description != null){
						String description = item_description.item(0).getFirstChild().getNodeValue();


						Pattern p = Pattern.compile("<\\s*img.*src\\s*=\\s*([\\\"'])?([^ \\\"']*)[^>]*>");
						Matcher m = p.matcher(description);
						if (m.find()) {
							String src = m.group(2);//ここにURLが入る
							System.out.println(src);
							target.addImgurl(src);
						}



					}
				}catch(Exception e) {
				}

				target.setTitle(title);
				target.setUrl(link);

				list.add(target);

				// System.out.println(" title: " + title);
				// System.out.println(" link:  " + link + "\n");
			}
		} catch (IOException e) {
			System.out.println("IO Exception");
		} catch (ParserConfigurationException e) {
			System.out.println("Parser Configuration Exception");
		} catch (SAXException e) {
			System.out.println("SAX Exception");
		}
		return list;
	}


	static public String sha1Digest( String str ) {
        StringBuffer result = new StringBuffer( "" );
        try {
            MessageDigest md = MessageDigest.getInstance( "SHA1" ); /* MD5のときは"MD5"とすればいいですよ！ */
            md.update( str.getBytes() );
            byte[] digest = md.digest();

            for ( int i = 0; i < digest.length; i++ ) {
                if ( ( digest[ i ] & 0x0ff ) / 16 == 0 ) {
                    result.append( "0" );
                }
                result.append( Integer.toHexString( digest[ i ] & 0x0ff ) );
            }

        } catch ( Exception e ) {}

        return result.toString();
    }


}
