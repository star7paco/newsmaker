package com.s7soft.newsmaker.translation;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TranslationUtil {

	private static final Logger log = Logger.getLogger(TranslationUtil.class.getName());

	public static List<String> keylist;

	static {
		keylist = new ArrayList<String>();

		keylist.add("해의 열려:호시노 아키");
		keylist.add("해의 빈 곳:호시노 아키");
		keylist.add("호시노 아키이:호시노 아키가");
		keylist.add("후지코 매:후지코 마이");
		keylist.add("실마리 고담:나카가와 쇼코");
		keylist.add("사 이루어 내려:카와나 시오리");
		keylist.add("서표:시오리");
		keylist.add("택산:사와야마");
		keylist.add("목재 질아시:키구치 아야");
		keylist.add("목재 질:키구치");
		keylist.add("건요자:이누이 요코");
		keylist.add("리전은:사토다 마이");
		keylist.add("홋타해 여름:홋타 유이카");
		keylist.add("땅데지:디지털 지상파");
		keylist.add("캐프챠:캡쳐");
		keylist.add("시동봐:토키토 아미");
		keylist.add("�:넊");
		keylist.add("차원일까:츠기하라 카나");
		keylist.add("타츠미나도자:타쯔미 나오코");
		keylist.add("소여름:코나츠");
		keylist.add("미나트:미나토");
		keylist.add("기려데스:이쁘군요");
		keylist.add("월궁토끼:츠키미야 우사기");
		keylist.add("코미케:코믹마켓");
		keylist.add("양식씨:히나가타 루리씨");
		keylist.add("상무사계:아이부 사키");
		keylist.add("와카츠키천하:와카츠키 치나츠");
		keylist.add("야마모토 가래나무:야마모토 아주사");
		keylist.add("굴호쿠신희:호리키타 마키");
		keylist.add("북내귀의:키타노 키이");
		keylist.add("후쿠다사기:후쿠다 사키");
		keylist.add("타치바나 수양자식:타치바나 유코");
		keylist.add("세크시:섹시");
		keylist.add("스마토:스마트");
		keylist.add("리스토:리스트");

	}

	public static String ChangeKey(String content) {
		for (String key : keylist) {
			String a = key.substring(0, key.indexOf(":"));
			String f = key.substring(key.indexOf(":") + 1);
			content = content.replaceAll(a, f);
		}
		// 특수문자 변환
		content = content.replaceAll("&amp;", "&");
		content = content.replaceAll("nbsp;", " ");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("&gt;", ">");
		content = content.replaceAll("&quot;", "\"");

		return content;
	}



	/**
	 * <pre>
	 * 변역기
	 * </pre>
	 *
	 * @author 고명환
	 * @param 일본어텍스트(utf8)
	 * @exception Exception
	 * @return 변역결과
	 */
	public static String getChangeHtml(String textjp) throws Exception {


//		Properties systemSettings = System.getProperties();
//		systemSettings.put("proxySet", "true");
//		systemSettings.put("http.proxyHost", "proxy.j-com.co.jp");
//		systemSettings.put("http.proxyPort", "8080");


		log.log(Level.FINEST, "START getChangeHtml" );
		if (textjp == null || textjp.trim().length() == 0) {
			return "";
		}

		String teg = "";
		textjp = textjp.replaceAll("〜", "~");
		textjp = textjp.replaceAll("—", "-");
		textjp = textjp.replaceAll("、", ",");
		textjp = textjp.replaceAll("	", "");
		textjp = textjp.replaceAll("<", "\n<");
		textjp = textjp.replaceAll(">", ">\n");
		textjp = textjp.replaceAll("\n\n", "\n");
		textjp = textjp.replaceAll("　", " ");

		StringTokenizer st = new StringTokenizer(textjp, "\n");
		String ret_str = "";

		int count = 0;
		for (; st.countTokens() > 0;) {
			String text = st.nextToken();
			if (text.indexOf("<a") == 0 || text.indexOf("<img") == 0) {
				teg = teg + text + ",";
				text = "[stringteg" + count + "]";
				count++;
			}

			ret_str = ret_str + text + "\n";

		}
//		int total = 0;
//		total = count;
		String temp = "";
		String textkr = "";
		boolean flg = false;
		// URLConnection의 스트림의 방향을 출력으로 설정
		// URLConnection conn이 존재한다고 가정
		URL url = new URL("http://www.excite.co.jp/world/korean/");
		URLConnection conn1 = url.openConnection();

		// 출력 스트림 제어
		conn1.setDoOutput(true);

		ret_str = URLEncoder.encode(ret_str, "UTF-8");
		OutputStream os = conn1.getOutputStream();
		PrintWriter out = new PrintWriter(os);
		out.println("before=" + ret_str
				+ "&wb_lp=JAKO&start=+%E7%BF%BB+%E8%A8%B3+&after=%3F%3F%3F");
		out.close();

		// 입력 스트림 제어

		InputStream is = conn1.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		while ((temp = br.readLine()) != null) {
			String chk = temp;
			if (!flg) {
				if (temp.indexOf("textarea") >= 0 && temp.indexOf("after") >= 0) {
					flg = true;
					// 앞 테그를 잘라냄(최초 일 회 실행)
					int start = temp.indexOf(">") + 1;
					if (start > 0) {
						temp = temp.substring(start);
					}
				}
			}
			if (flg) {
				// 뒤 테그를 잘라냄
				int end = temp.lastIndexOf("<");
				if (end > 0) {
					temp = temp.substring(0, end);
				}
				// 변수에 저장
				textkr = textkr + temp + "\n";
				// 마지막 체크
				if (chk.indexOf("</textarea>") >= 0) {
					break;
				}
			}
		}
		br.close();
		textkr = textkr.replaceAll("&lt;", "<");
		textkr = textkr.replaceAll("&gt;", ">");
		textkr = textkr.replaceAll("&quot;", "\"");
		textkr = textkr.replaceAll("</textarea>", "");
		textkr = textkr.replaceAll("&#010;", "");


		textkr = ChangeKey(textkr);// 변역기 보정

		String tegs[] = teg.split(",");

//		boolean ad_flg = true;
//
//		for (int i = 0; i < tegs.length; i++) {
//			// 사진의 중간에광고 삽입
//			if (ad_flg && total / 2 == i) {
//				String ad = "<br><script type=\"text/javascript\"><!--\ngoogle_ad_client = \"pub-7534285248466198\";\n/* 박스 336x280, 작성됨 08. 4. 21 */\ngoogle_ad_slot = \"4530726622\";\ngoogle_ad_width = 336;\ngoogle_ad_height = 280;\n//--></script>\n<script type=\"text/javascript\"src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n</script><br><br>";
//				textkr = textkr.replace("[stringteg" + i + "]", tegs[i] + ad);
//				ad_flg = false;
//			} else {
//				textkr = textkr.replace("[stringteg" + i + "]", tegs[i]);
//			}
//
//		}

		for (int i = 0; i < tegs.length; i++) {
			textkr = textkr.replace("[stringteg" + i + "]", tegs[i]);
		}
//		if(textkr.indexOf("</textarea>") >= 0){
//			textkr = getChangeHtml(textjp);
//		}
		return textkr;

	}

	/**
	 * <pre>
	 * 변역기
	 * </pre>
	 *
	 * @author 고명환
	 * @param 일본어텍스트(utf8)
	 * @exception Exception
	 * @return 변역결과
	 */
	public static String getChangeHtmlJaToKo(String textjp) throws Exception {


//		Properties systemSettings = System.getProperties();
//		systemSettings.put("proxySet", "true");
//		systemSettings.put("http.proxyHost", "proxy.j-com.co.jp");
//		systemSettings.put("http.proxyPort", "8080");


		log.log(Level.FINEST, "START getChangeHtml" );
		if (textjp == null || textjp.trim().length() == 0) {
			return "";
		}

		String teg = "";
		textjp = textjp.replaceAll("〜", "~");
		textjp = textjp.replaceAll("—", "-");
		textjp = textjp.replaceAll("、", ",");
		textjp = textjp.replaceAll("	", "");
		textjp = textjp.replaceAll("<", "\n<");
		textjp = textjp.replaceAll(">", ">\n");
		textjp = textjp.replaceAll("\n\n", "\n");
		textjp = textjp.replaceAll("　", " ");

		StringTokenizer st = new StringTokenizer(textjp, "\n");
		String ret_str = "";

		int count = 0;
		for (; st.countTokens() > 0;) {
			String text = st.nextToken();
			if (text.indexOf("<a") == 0 || text.indexOf("<img") == 0) {
				teg = teg + text + ",";
				text = "[stringteg" + count + "]";
				count++;
			}

			ret_str = ret_str + text + "\n";

		}
//		int total = 0;
//		total = count;
		String temp = "";
		String textkr = "";
		boolean flg = false;
		// URLConnection의 스트림의 방향을 출력으로 설정
		// URLConnection conn이 존재한다고 가정
		URL url = new URL("http://www.excite.co.jp/world/korean/");
		URLConnection conn1 = url.openConnection();

		// 출력 스트림 제어
		conn1.setDoOutput(true);

		ret_str = URLEncoder.encode(ret_str, "UTF-8");
		OutputStream os = conn1.getOutputStream();
		PrintWriter out = new PrintWriter(os);
		out.println("before=" + ret_str
				+ "&wb_lp=KOJA&start=+%E7%BF%BB+%E8%A8%B3+&after=%3F%3F%3F");
		out.close();

		// 입력 스트림 제어

		InputStream is = conn1.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);

		while ((temp = br.readLine()) != null) {
			String chk = temp;
			if (!flg) {
				if (temp.indexOf("textarea") >= 0 && temp.indexOf("after") >= 0) {
					flg = true;
					// 앞 테그를 잘라냄(최초 일 회 실행)
					int start = temp.indexOf(">") + 1;
					if (start > 0) {
						temp = temp.substring(start);
					}
				}
			}
			if (flg) {
				// 뒤 테그를 잘라냄
				int end = temp.lastIndexOf("<");
				if (end > 0) {
					temp = temp.substring(0, end);
				}
				// 변수에 저장
				textkr = textkr + temp + "\n";
				// 마지막 체크
				if (chk.indexOf("</textarea>") >= 0) {
					break;
				}
			}
		}
		br.close();
		textkr = textkr.replaceAll("&lt;", "<");
		textkr = textkr.replaceAll("&gt;", ">");
		textkr = textkr.replaceAll("&quot;", "\"");
		textkr = textkr.replaceAll("</textarea>", "");

		textkr = ChangeKey(textkr);// 변역기 보정

		String tegs[] = teg.split(",");

//		boolean ad_flg = true;
//
//		for (int i = 0; i < tegs.length; i++) {
//			// 사진의 중간에광고 삽입
//			if (ad_flg && total / 2 == i) {
//				String ad = "<br><script type=\"text/javascript\"><!--\ngoogle_ad_client = \"pub-7534285248466198\";\n/* 박스 336x280, 작성됨 08. 4. 21 */\ngoogle_ad_slot = \"4530726622\";\ngoogle_ad_width = 336;\ngoogle_ad_height = 280;\n//--></script>\n<script type=\"text/javascript\"src=\"http://pagead2.googlesyndication.com/pagead/show_ads.js\">\n</script><br><br>";
//				textkr = textkr.replace("[stringteg" + i + "]", tegs[i] + ad);
//				ad_flg = false;
//			} else {
//				textkr = textkr.replace("[stringteg" + i + "]", tegs[i]);
//			}
//
//		}

		for (int i = 0; i < tegs.length; i++) {
			textkr = textkr.replace("[stringteg" + i + "]", tegs[i]);
		}
//		if(textkr.indexOf("</textarea>") >= 0){
//			textkr = getChangeHtml(textjp);
//		}
		return textkr;

	}




	public static void main(String s[]) throws Exception {
		String kr = getChangeHtml("北が前線部隊増強か 韓国警戒");

		System.out.println(kr);

//		if(urlBD.updateList()){
//			System.out.println("OK");
//		}

	}

}
