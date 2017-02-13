package com.glorial.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static final int ALIGN_NONE   = 0;
	public static final int ALIGN_LEFT   = 1;
	public static final int ALIGN_RIGHT  = 2;
	public static final int ALIGN_CENTER = 3;

	/**
	 * xml에 가능한 문자열로 변환(특수문자 처리)
	 *
	 * @param str
	 *            원문
	 * @return XML에 사용 가능한 문자열로 변환
	 */
	public static String toXml(String str) {
		if (str == null)
			return null;
		if (str.indexOf('&') > 0)
			str = str.replaceAll("&", "&#38;");
		if (str.indexOf('<') > 0)
			str = str.replaceAll("<", "&lt;");
		if (str.indexOf('>') > 0)
			str = str.replaceAll(">", "&gt;");

		return str;
	}

	/**
	 * byte배열 -> 문자열. WAS 별로 인코딩이 달라질 수 있어 하나로 통일.
	 *
	 * @param b
	 *            byte배열
	 * @return 문자열
	 */
	public static String getStringFromBytes(byte[] b) {
		String r = null;
		try {
			r = new String(b, "euc-kr");
		} catch (UnsupportedEncodingException e) {
		}
		return r;
	}

	/**
	 * 문자열 -> byte배열. WAS별로 인코딩이 달라질수 있어 하나로 통일
	 *
	 * @param str
	 *            문자열
	 * @return byte배열
	 */
	public static byte[] getBytesFromString(String str) {
		byte[] b = null;
		try {
			b = str.getBytes("euc-kr");
		} catch (UnsupportedEncodingException ex) {
		}
		return b;
	}

	/**
	 * 사업자번호에 구분자 넣음(ex 1018116731 -> 101-81-16731 )
	 *
	 * @param no
	 *            사업자번호(10자리)
	 * @return 구분자 있는 사업자번호
	 */
	public static String toCompanyNo(String no) {
		if (no == null)
			return "";
		no = no.trim();
		if (no.length() != 10)
			return no;

		return no.substring(0, 3) + "-" + no.substring(3, 5) + "-"
				+ no.substring(5);
	}
	/**
	 * 주민번호 or 법인번호 구분자 넣음(ex 123456-1234567 ->  123456-1234567)
	 *
	 * @param no  주민빈호 or 법인번호(13자리)
	 * @return 구분자 있는 주민번호 or 법인번호
	 */
	public static String toBizNo(String no) {
		if (no == null)
			return "";
		no = no.trim();
		if (no.length() != 13)
			return no;

		return no.substring(0, 6) + "-" + no.substring(6);
	}

	/**
	 * 전화번호에 구분자 넣음(ex111111111 -> 111-111-1111)
	 *
	 * @param no
	 *            전화번호(최소 9자리, 최대 12자리)
	 * @return 구분자 있는 전화번호
	 */
	public static String toPhoneNo(String no) {
		if (no == null)
			return "";
		int len = no.length();
		if (len < 9 || len > 12)
			return no;

		int gb1 = 3;
		int gb2 = 4;
		if (len - 4 == 5)
			gb1 = 2;

		if (len - gb1 == 7)
			gb2 = 3;

		no = no.substring(0, gb1) + "-" + no.substring(gb1, gb1 + gb2) + "-"
				+ no.substring(gb1 + gb2, gb1 + gb2 + 4);

		return no;
	}

	/**
	 * 문자열 중간에 삽입
	 *
	 * @param str1
	 *            원문
	 * @param pos
	 *            위치
	 * @param str2
	 *            삽입할 문자열
	 * @return String 문자열
	 */
	public static String insert(String str1, int pos, String str2) {
		return str1.substring(0, pos) + str2 + str1.substring(pos);
	}

	/**
	 * 문자열 전체 치환, 원문에서 어는 문자를 다른 문자열로 바꿈
	 *
	 * @param str
	 *            원문
	 * @param ch
	 *            문자
	 * @param replacement
	 *            바꿀문자열
	 * @return 바뀐 문자열
	 */
	public static String replace(String str, int ch, String replacement) {
		StringBuffer ret = new StringBuffer("");
		int p, oldp = 0;
		while ((p = str.indexOf(ch, oldp)) >= 0) {
			ret.append(str.substring(oldp, p));
			ret.append(replacement);
			oldp = p + 1;
		}
		ret.append(str.substring(oldp));
		return ret.toString();
	}

	/**
	 * 문자열 전체 치환, 원문에서 어느 문자열1을 다른 문자열2로 바꿈
	 *
	 * @param str
	 *            원문
	 * @param old
	 *            문자열1
	 * @param replacement
	 *            문자열2
	 * @return 바뀐 문자열
	 */
	public static String replace(String str, String old, String replacement) {
		StringBuffer ret = new StringBuffer();
		if (str == null)
			return "";
		int p, oldp = 0;
		int oldlen = old.length();
		while ((p = str.indexOf(old, oldp)) >= 0) {
			ret.append(str.substring(oldp, p));
			ret.append(replacement);
			oldp = p + oldlen;
		}
		ret.append(str.substring(oldp));
		return ret.toString();
	}

	/**
	 * 프로젝트 상의 문자열 인코딩 정의 ( DB 등에서 한글 인코딩 문제 해결을 위한 메서드 )
	 *
	 * @param str
	 *            원문
	 * @return 인코딩 바뀐 문자열
	 */
	public static String to8859(String str) {
		String r = null;
		try {
			r = new String(str.getBytes("euc-kr"), "8859_1");
		} catch (Exception ex) {
		}
		return r;
	}

	/**
	 * URL에 사용가능한 문자열로 변환
	 *
	 * @param str
	 *            원문
	 * @return 변환된 문자열
	 */
	public static String urlEncode(String str) {
		String r = null;
		// try {
		try {
			r = java.net.URLEncoder.encode(str, "euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// }
		// catch(Exception ex){}
		return r == null ? "" : r;
	}

	/**
	 * url encode된 문자열을 decode
	 *
	 * @param str
	 *            문자열
	 * @return decode된문자열
	 */
	public static String urlDecode(String str) {
		String r = null;
		try {
			r = java.net.URLDecoder.decode(str, "euc-kr");
		} catch (Exception ex) {
		}
		return r == null ? "" : r;
	}

	/**
	 * html 문자중 특수 문자( & < > 등 )를 화면에 표시될 수 있도록 변환
	 *
	 * @param str
	 *            원문
	 * @param initV
	 *            null일경우 반환되는 문자열
	 * @return 변환된 문자열
	 */
	public static String htmlEncode(Object str, String initV) {

		return xmlEncode(str, initV);
	}

	public static String xmlEncode(Object str ) {
		return xmlEncode(str,"");
	}

	public static String xmlEncode(Object str, String initV) {
		if (str == null)
			return initV;
		String d = str.toString();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<d.length();i++) {
			char ch = d.charAt(i);
			switch(ch) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\"':
					sb.append("&quot;");
					break;
				case '\'':
					sb.append("&#39;");
					break;
				default:
					sb.append(ch);
					break;
			}
		}
		return sb.toString();
	}

	/**
	 * html 문자중 특수 문자( & < > 등 )를 화면에 표시될 수 있도록 변환
	 *
	 * @param str
	 *            원문
	 * @return 변환된 문자열
	 */
	public static String htmlEncode(Object str) {
		return htmlEncode(str, "");
	}

	/**
	 * 날짜 를 yyyy-M-d 형태로 변환
	 *
	 * @param d
	 *            날짜
	 * @return 포맷된 문자열
	 */
	public static String formatDateYMD(Object dt) {
		String dstr = "";

		try {
			Calendar c = Calendar.getInstance();
			Date d = (Date) dt;
			c.setTime(d);
			StringBuffer sb = new StringBuffer();
			sb.append(c.get(Calendar.YEAR));
			sb.append('-');
			sb.append(c.get(Calendar.MONTH) + 1);
			sb.append('-');
			sb.append(c.get(Calendar.DAY_OF_MONTH));
			dstr = sb.toString();
		} catch (Exception ex) {
		}
		return dstr;
	}

	/**
	 * 날짜형태 문자열을 Timestamp로 변환
	 *
	 * @param dstr
	 *            날짜
	 * @return Timestamp
	 */
	public static java.util.Date parseDate( String dstr )
	  {
		    if(dstr==null)
		        return null;
		    dstr = dstr.trim();
		    int p = dstr.indexOf(' ');

		    String datePart = null;
		    String timePart = null;

		    if(p>0) {
		    	datePart = dstr.substring(0,p);
		    	timePart = dstr.substring(p+1);
		    }
		    else {
		    	datePart = dstr;
		    }

		    String[] ds = null;

		    if(datePart.indexOf('-')>0)
		    	ds = StringUtil.split(datePart,'-');
		    else if(datePart.indexOf('.')>0)
		    	ds = StringUtil.split(datePart,'.');
		    else if(datePart.indexOf('.')>0)
		    	ds = StringUtil.split(datePart,'/');
		    else
		    	return toDateFromYYYYMMDD(datePart);

		    int y = 0, M = 0, d = 0, H=0,m=0,s=0;

		    Calendar c = Calendar.getInstance();
		    try {
		    	y = Integer.parseInt(ds[0]);
		    	M = Integer.parseInt(ds[1]);
		    	d = Integer.parseInt(ds[2]);
		    }
		    catch(Exception ex){
		    	return null;
		    } // 날짜는 잘못 입력시 null

		    if(timePart!=null) {
		    	int dp = timePart.indexOf('.');
		    	if(dp>0)
		    		timePart=timePart.substring(0,dp);// 밀리초는 무시

		    	String[] ts = StringUtil.split(timePart,':');
		    	try {
		    		H = Integer.parseInt(ts[0]);
		    		m = Integer.parseInt(ts[1]);
		    		if(ts.length>2) // 초는 생략가능
		    			s = Integer.parseInt(ts[2]);

		    	}
		    	catch(Exception ex) {
		    		H=m=s=0;
		    	} // 시간은 잘못입력시 0시 0분 0초
		    }

		    try {
		      c.set( y, M-1, d, H,m,s );
		    }
		    catch( Exception ex) {return null; }
		    return new java.util.Date(c.getTimeInMillis());
	  }

	/**
	 * YYYYMMDD형태 문자열을 Date 형으로 변환
	 *
	 * @param yyyyMMdd
	 *            날짜문자열
	 * @return Timestamp
	 */
	public static java.util.Date toDateFromYYYYMMDD(String yyyyMMdd) {
		java.util.Date d = null;

		if (yyyyMMdd == null)
			return null;
		int l = yyyyMMdd.length();

		if (l >= 8) {
			Calendar c = Calendar.getInstance();
			try {
				int nYmd = Integer.parseInt(yyyyMMdd.substring(0, 8));
				d = new java.util.Date(0);
				c.set(nYmd / 10000, (nYmd / 100) % 100 - 1, nYmd % 100);
			} catch (Exception ex) {
				c = null;
			}
			if (c != null) {
				try {
					String hhmmdd = yyyyMMdd.substring(8);
					c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hhmmdd
							.substring(0, 2)));
					c.set(Calendar.MINUTE, Integer.parseInt(hhmmdd.substring(2,
							4)));
					c.set(Calendar.SECOND, Integer.parseInt(hhmmdd.substring(4,
							6)));
				} catch (Exception ex) {
				}
				c.set(Calendar.MILLISECOND, 0);
				d = new java.util.Date(c.getTimeInMillis());
			}
		}
		return d;
	}

	/**
	 * 현재시간(Timestamp)
	 *
	 * @return 현재시간
	 */
	public static java.util.Date getCurrentDate() {
		return new java.util.Date();
	}

	/**
	 * 현재날짜(Timestamp). 0시 0분 0초
	 *
	 * @return 현재날짜
	 */
	public static java.util.Date getCurrentDateOnly() {
		Calendar c = Calendar.getInstance();

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return new java.util.Date(c.getTimeInMillis());
	}

	/**
	 * 문자열중 리턴 코드 를 &lt;br&gt; 코드로 바꿔서 브라우저상에서 행바꿈이 되도록 변환
	 *
	 * @param str
	 *            원문
	 * @return 변환된 문자열
	 */
	public static String nl2br(String str) {
		return replace(str, '\n', "<br>\n");
	}

	/**
	 * 정수형으로 되어있는 ip 값을 실제 원 ip 문자열로 변환
	 *
	 * @param ip
	 *            IP(정수)
	 * @return IP(문자열)
	 */
	public static String int2ip(int ip) {
		return ((ip >> 24) & 0xff) + "." + ((ip >> 16) & 0xff) + "."
				+ ((ip >> 8) & 0xff) + "." + ((ip) & 0xff);
	}

	/**
	 * 문자열을 특정 문자 기준으로 split
	 *
	 * @param str
	 *            원문
	 * @param ch
	 *            구분자
	 * @return split된 문자열 배열
	 */
	public static String[] split(String str, int ch) {
		int p;
		int lp = 0;
		// String s;
		ArrayList al = new ArrayList();
		while (true) {
			p = str.indexOf(ch, lp);
			if (p < 0) {
				al.add(str.substring(lp));
				break;
			}
			al.add(str.substring(lp, p));
			lp = p + 1;
		}

		return toStringArray(al);
	}

	public static String[] split(String str, String gb) {
		int p;
		int lp = 0;
		// String s;
		ArrayList al = new ArrayList();
		int gbl = gb.length();
		while (true) {
			p = str.indexOf(gb, lp);
			if (p < 0) {
				al.add(str.substring(lp));
				break;
			}
			al.add(str.substring(lp, p));
			lp = p + gbl;
		}

		return toStringArray(al);
	}

	/**
	 * 문자열 ip 를 정수로 변환
	 *
	 * @param ip
	 *            IP(문자열)
	 * @return IP(정수)
	 */
	public static int ip2int(String ip) {
		int ret;
		try {
			String[] s = split(ip, '.');
			ret = (Integer.parseInt(s[0]) << 24)
					| (Integer.parseInt(s[1]) << 16)
					| (Integer.parseInt(s[2]) << 8) | (Integer.parseInt(s[3]));
		} catch (Exception ex) {
			ret = 0;
		}
		return ret;
	}

	/**
	 * 문자열을 정수로 변환
	 *
	 * @param str
	 *            문자열
	 * @param no
	 *            실패시 반환값
	 * @return 값
	 */
	public static int toInt(Object str, int no) {
		try {
			no = Integer.parseInt((String) str, 10);
		} catch (Exception ex) {
		}
		return no;
	}

	/**
	 * 문자열을 정수로 변환. 실패시 -1 반환
	 *
	 * @param str
	 *            문자열
	 * @return 값
	 */
	public static int toInt(Object str) {
		return toInt(str, -1);
	}

	/**
	 * 문자열을 long 정수로 변환
	 *
	 * @param str
	 *            문자열
	 * @param no
	 *            실패시 반환값
	 * @return 값
	 */
	public static long toLong(String str, long no) {
		try {
			no = Long.parseLong(str);
		} catch (Exception ex) {
		}
		return no;
	}

	/**
	 * 문자열을 long 정수로 변환, 실패시 -1 반환
	 *
	 * @param str
	 *            문자열
	 * @return 값
	 */
	public static long toLong(String str) {
		return toLong(str, -1);
	}

	/**
	 * 널null 이 아니면 원문, 널인 경우 다른 문자열 반환
	 *
	 * @param str
	 *            원문
	 * @param nl
	 *            null인경우 반환될 문자열
	 * @return 반환값
	 */
	public static String nvl(Object str, String nl) {
		return str == null ? nl : str.toString().trim();
	}

	/**
	 * 널null 이 아니면 원문, 널인 경우 공백문자열("") 반환
	 *
	 * @param str
	 *            원문
	 * @return 반환값
	 */
	public static String nvl(Object str) {
		return nvl(str, "");
	}

	/**
	 * 널null 이 아니면 원문, 널인 경우 공백문자열("&nbsp;") 반환
	 *
	 * @param str
	 *            원문
	 * @return 반환값
	 */
	public static String hnvl(Object str) {
		return nvl2(str, "&nbsp;");
	}
	public static String nvl2(Object str, String nl) {
		return str == null || str.toString().trim().length()==0 ? nl : str.toString().trim();
	}

	/**
	 * SQL에 안전한 문자열로 변환. 공백이면 null
	 *
	 * @param str
	 *            문자열
	 * @return SQL에 안전한 문자열
	 */
	public static String dbEncodeNull(Object ostr) {
		String str = dbEncode(ostr);
		return (str == null || str.length() == 0) ? null : str;
	}

	/**
	 * trim 후 공백이면 null로 바꿈
	 *
	 * @param str
	 *            문자열
	 * @return trim된 문자열
	 */
	public static String trimToNull(String str) {
		if (str == null)
			return null;
		str = str.trim();
		return (str.length() == 0) ? null : str;
	}

	/**
	 * SQL에 안전한 문자열로 변환
	 *
	 * @param str
	 *            문자열
	 * @return SQL에 안전한 문자열
	 */
	public static String dbEncode(Object str) {
		if (str == null)
			return null;
		return replace(str.toString().trim(), '\'', "\'\'");
	}

	/**
	 * 역슬래시 를 슬래시 두개로 바꿈
	 *
	 * @param s
	 *            원문
	 * @return 반환된 문자열
	 */
	public static String escape(String s) {
		return replace(s, "\\", "\\\\");
	}

	protected static String readNumber1000(String ns) {
		String[] jari = { "", "십", "백", "천" };
		String[] su = { "일", "이", "삼", "사", "오", "육", "칠", "팔", "구" };
		String n = ns.toString();
		String ss = "";
		int nl = n.length();
		for (int i = 0; i < nl; i++) {
			int c = n.charAt(nl - i - 1) - '0';
			if (c > 0)
				ss = su[c - 1] + jari[i] + ss;
		}
		return ss;
	}

	/**
	 * 숫자를 한글 숫자로 바꿈( ex 1002 -> 천이 )
	 *
	 * @param n
	 *            숫자
	 * @return 한글숫자
	 */
	public static String getHanNumber(long n) {
		return getHanNumber(new Long(n).toString());
	}

	public static String getHanNumber(BigDecimal n) {
		if(n==null) return "";
		return getHanNumber(n.toString());
	}


	/**
	 * 숫자를 한글 숫자로 바꿈( ex 1002 -> 천이 )
	 *
	 * @param n
	 *            숫자(String형)
	 * @return 한글숫자
	 */
	public static String getHanNumber(String ns) {
		if(ns==null) return "";
		String n = StringUtil.replace(ns, ',', "");
		int dp = n.indexOf('.');

		if (dp >= 0)
			n = n.substring(0, dp);

		int l = n.length();
		String s = "";
		String[] jari = { "", "만", "억", "조", "경", "해" };
		int i = 0;
		String ss;
		while (l > 4) {
			ss = readNumber1000(n.substring(l - 4));
			if (ss != "")
				s = ss + jari[i] + s;
			l -= 4;
			n = n.substring(0, l);
			i++;
		}
		ss = readNumber1000(n);
		if (ss != "")
			s = ss + jari[i] + s;
		if (s == "")
			s = "영";
		return s;
	}

	/**
	 * 주민등록번호에 - 기호 넣기
	 *
	 * @param no
	 *            String
	 * @return String
	 */
	public static String toRRN(String no) {
		return toRRN(no, true);
	}

	public static String toRRN(String no, boolean isView) {
		if (no == null)
			return "";
		no = no.trim();
		return (no.length() == 13) ? no.substring(0, 6) + "-" + (isView?no.substring(6):"******")
				: (no.equals("0") ? "" : no);
	}

	static char getHexB(int no) {
		return (char) (no >= 10 ? no - 10 + 'a' : '0' + no);
	}

	static void appendHex(StringBuffer b, byte n) {
		b.append(getHexB((n & 0xf0) >> 4));
		b.append(getHexB((n & 0x0f)));
	}

	/**
	 * 바이트 배열을 hex 문자열로 바꿈
	 *
	 * @param b
	 *            바이트배열
	 * @return hex 문자열
	 */
	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			appendHex(sb, b[i]);
		}
		return sb.toString();
	}

	public static String toHexString( long v ) {
		return toHexString( new byte[] {
				(byte)((v>>24) & 0xff),
				(byte)((v>>16) & 0xff),
				(byte)((v>>8 ) & 0xff),
				(byte)((v    ) & 0xff)
		});
	}


	/**
	 * 숫자앞에 0 붙이기 ex) StringUtil.padZero( 12345, 10 ) = 0000012345
	 *
	 * @param no
	 *            숫자
	 * @param co
	 *            전체 길이
	 * @return 앞에 0 이 붙은 문자열
	 */
	public static String padZero(int no, int co) {
		String nos = "000000000000000000000000000000000000" + no;
		return nos.substring(nos.length() - co);
	}

	  public static String padZero( String no, int co )
	  {
		    String nos = "000000000000000000000000000000000000"+no;
		    return nos.substring(nos.length()-co);
	  }

	/**
	 * 문자열 배열을 합쳐서 한 문자열로 만듬. 구분자는 ch
	 *
	 * @param srcs
	 *            문자열배열
	 * @param ch
	 *            구분자
	 * @return 합쳐진문자열
	 */
	public static String join(String[] srcs, char ch) {
		if (srcs == null)
			return "";
		StringBuffer sb = new StringBuffer(srcs[0]);
		for (int i = 1; i < srcs.length; i++) {
			sb.append(ch);
			sb.append(srcs[i]);
		}
		return sb.toString();
	}

	/**
	 * Object 배열을 합쳐서 하나의 문자열로 만듬. 구분자는 ch
	 *
	 * @param srcs
	 *            문자열배열
	 * @param ch
	 *            구분자
	 * @return 합쳐진문자열
	 */
	public static String join(Object[] srcs, char ch) {
		if (srcs == null)
			return "";
		StringBuffer sb = new StringBuffer(srcs[0].toString());
		for (int i = 1; i < srcs.length; i++) {
			sb.append(ch);
			sb.append(srcs[i].toString());
		}
		return sb.toString();
	}

	/**
	 * 문자열 배열을 합쳐서 한 문자열로 만듬
	 *
	 * @param srcs
	 *            문자열배열
	 * @param s
	 *            구분자
	 * @return 합쳐진문자열
	 */
	public static String join(String[] srcs, String s) {
		if (srcs == null)
			return "";
		StringBuffer sb = new StringBuffer(srcs[0]);
		for (int i = 1; i < srcs.length; i++) {
			sb.append(s);
			sb.append(srcs[i]);
		}
		return sb.toString();
	}

	/**
	 * Object 배열을 합쳐서 한 문자열로 만듬
	 *
	 * @param srcs
	 *            Object배열
	 * @param s
	 *            구분자
	 * @return 합쳐진문자열
	 */
	public static String join(Object[] srcs, String s) {
		if (srcs == null)
			return "";
		StringBuffer sb = new StringBuffer(srcs[0].toString());
		for (int i = 1; i < srcs.length; i++) {
			sb.append(s);
			sb.append(srcs[i].toString());
		}
		return sb.toString();
	}

	/**
	 * yyyy-MM-dd 형식의 문자열을 YYYYMMDD 형태로 변환
	 *
	 * @param d
	 *            yyyy-MM-dd 형식의 문자열
	 * @return yyyyMMdd 형태의 문자열
	 */
	public static String toYYYYMMDDFromYMD(String d) {
		String r = "";
		if (d == null)
			d = "";
		String[] s = d.split("-");
		if (s.length == 3) {
			try {
				r = String
						.valueOf(Integer.parseInt(s[0], 10) * 10000
								+ Integer.parseInt(s[1]) * 100
								+ Integer.parseInt(s[2]));
			} catch (Exception ex) {
			}
		}
		return r;
	}

	/**
	 * yyyyMMdd 형태의 문자열을 yyyy-MM-dd 형태로 변환
	 *
	 * @param d
	 *            yyyyMMdd 문자열
	 * @return yyyy-MM-dd 문자열
	 */
	public static String toDateYMDFromYYYYMMDD(String d) {
		if (d == null)
			return "";
		d = d.trim();
		if (d.equals(""))
			return "";
		String ds = d + "        ";
		return (ds.substring(0, 4) + "-" + ds.substring(4, 6) + "-" + ds
				.substring(6)).trim();
	}

	/**
	 * 숫자로된 문자열을 BigDecimal 형식으로 바꿈. 콤마(,) 는 무시함
	 *
	 * @param n
	 *            숫자로된 문자열
	 * @return BigDecimal값
	 */
	public static BigDecimal parseBigDecimal(String n, int scale ) {
		BigDecimal r = null;
		if(n==null)return null;
		n = StringUtil.replace(n, ',', "");

		try {
			r = new BigDecimal(Double.parseDouble(n));
		} catch (Exception ex) {
			r = null;
		}

		if(scale>=0)
			r = r.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return r;
	}

	public static BigDecimal parseBigDecimal(String n ) {
		return parseBigDecimal(n,-1);
	}

	/**
	 * 숫자로된 문자열을 long 으로 바꿈. 콤마(,)는 무시함
	 *
	 * @param n
	 *            숫자로된 문자열
	 * @param initv
	 *            변환실패시 값
	 * @return long값
	 */
	public static long parseLong(String n, long initv) {
		long no;
		try {
			no = Long.parseLong(StringUtil.replace(n, ',', ""));
		} catch (Exception ex) {
			no = initv;
		}
		return no;
	}

	/**
	 * 숫자로된 문자열을 long 으로 바꿈. 콤마(,)는 무시함
	 *
	 * @param n
	 *            숫자로된 문자열
	 * @return long값
	 */
	public static long parseLong(String n) {
		return parseLong(n, 0);
	}

	/**
	 * Collection 의 모든 값을 스트링배열로 변환
	 *
	 * @param cl
	 *            Collection
	 * @return String 배열
	 */
	public static String[] toStringArray(java.util.Collection cl) {
		Object[] al = cl.toArray();
		int sz = al.length;
		String[] r = new String[sz];
		for (int i = 0; i < sz; i++)
			r[i] = (String) al[i];
		return r;
	}

	/**
	 * 스트링 배열을 스트링으로 반환
	 * @param s
	 * @param seperater
	 * @return
	 */
	public static String getStringFromStringArray(String[] s, String seperater){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length; i++){
			sb.append(s[i]);
			if(i < s.length-1){
				sb.append(seperater);
			}
		}
		return sb.toString();
	}

	private static SimpleDateFormat defaultDateFormat = null;

	private static DecimalFormat defaultDecimalFormat = null;

	static {
		defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		defaultDecimalFormat = new DecimalFormat("#,##0.#####");
	}

	/**
	 * 날짜를 기본 포맷(yyyy-MM-dd) 으로 문자열로 변환
	 *
	 * @param d
	 *            날짜
	 * @return 문자열
	 */
	public static String format(Date d) {
		if (d == null)
			return "";
		return defaultDateFormat.format(d);
	}

	/**
	 * 숫자를 기본 포맷(#,##0.#####) 으로 문자열로 변환
	 *
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(BigDecimal d) {
		if (d == null)
			return "";
		return defaultDecimalFormat.format(d);
	}

	/**
	 * 숫자를 기본 포맷(#,##0.#####) 으로 문자열로 변환
	 *
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(double d) {
		return defaultDecimalFormat.format(d);
	}

	/**
	 * 숫자를 기본 포맷(#,##0.#####) 으로 문자열로 변환
	 *
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(long d) {
		return defaultDecimalFormat.format(d);
	}

	/**
	 * 날짜를 format 에 해당하는 형식으로 변환
	 *
	 * @param format
	 *            포맷(ex yyyy-MM-dd HH:mm:ss )
	 * @param d
	 *            날짜
	 * @return 문자열
	 */
	public static String format(String format, Date d) {
		if (d == null)
			return "";
		return FormatterCache.getDateFormat(format).format(d);
	}

	/**
	 * 숫자를 format 에 해당하는 형식으로 변환
	 *
	 * @param format
	 *            포맷(ex #,##0.000 )
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(String format, java.math.BigDecimal d) {
		if (d == null)
			return "";
		return FormatterCache.getDecimalFormat(format).format(d);
	}

	/**
	 * 숫자를 format 에 해당하는 형식으로 변환
	 *
	 * @param format
	 *            포맷(ex #,##0.000 )
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(String format, double d) {
		return FormatterCache.getDecimalFormat(format).format(d);
	}

	/**
	 * 숫자를 format 에 해당하는 형식으로 변환
	 *
	 * @param format
	 *            포맷(ex #,##0.000 )
	 * @param d
	 *            숫자
	 * @return 문자열
	 */
	public static String format(String format, long d) {
		return FormatterCache.getDecimalFormat(format).format(d);
	}

	/**
	 * 해당 format 의 날짜 Formatter ( java.text.Format )를 반환
	 *
	 * @param format
	 *            포맷
	 * @return java.text.Format
	 */
	public static java.text.Format getDateFormatter(String format) {
		return FormatterCache.getDateFormat(format);
	}

	/**
	 * 해당 format 의 숫자 Formatter ( java.text.Format )를 반환
	 *
	 * @param format
	 *            포맷
	 * @return java.text.Format
	 */
	public static java.text.Format getDecimalFormatter(String format) {
		return FormatterCache.getDecimalFormat(format);
	}

	/**
	 * 문자열을 byte단위( 즉, 영문1byte 한글은 2byte )로 왼쪽에서 잘라냄
	 *
	 * @param str
	 *            문자열
	 * @param len
	 *            byte길이
	 * @return 잘려진 문자열
	 */
	public static String leftByte(String str, int len) {
		StringBuffer sb = new StringBuffer();

		int l = 0;
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch < 0 || ch > 127)
				l++;
			l++;
			if (l > len)
				break;
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String rtrim(String str) {
		int l = str.length();
		while (l > 0 && str.charAt(l - 1) == ' ')
			l--;
		if (l == 0)
			return "";
		return str.substring(0, l);
	}

	 public static String ellipsisText( int width, String text )
	  {
		  return ellipsisText( width, ALIGN_NONE, text );
	  }
	  public static String ellipsisText( int width, int align, String text )
	  {
		  return ellipsisText( width, align, text, null  );
	  }

	  public static String ellipsisText( int width, String text, String title )
	  {
		  return ellipsisText( width, ALIGN_NONE, text, title );
	  }

	  public static String ellipsisText( int width, int align, String text, String title )
	  {
		  StringBuffer sb = new StringBuffer();
		  sb.append("<span style='width:"+width+"px;");
		  switch(align) {
		  case ALIGN_LEFT: sb.append("text-align:left;");break;
		  case ALIGN_RIGHT: sb.append("text-align:right;");break;
		  case ALIGN_CENTER: sb.append("text-align:center;");break;
		  }
		  sb.append("overflow-x:hidden;text-overflow:ellipsis;'");
		  if(title!=null)
			  sb.append(" title=\""+(title)+"\"");
		  sb.append("><nobr>"+(text)+"</nobr></span>");

		  return sb.toString();
	  }

	  public static String ellipsisTextC( int width, int align, String text, String title )
	  {
		  StringBuffer sb = new StringBuffer();
		  sb.append("<span style='width:"+width+"px;");
		  switch(align) {
		  case ALIGN_LEFT: sb.append("text-align:left;");break;
		  case ALIGN_RIGHT: sb.append("text-align:right;");break;
		  case ALIGN_CENTER: sb.append("text-align:center;");break;
		  }
		  sb.append("overflow-x:hidden;text-overflow:ellipsis;cursor:hand;'");
		  if(title!=null)
			  sb.append(" title=\""+(title)+"\"");
		  sb.append("><nobr>"+(text)+"</nobr></span>");

		  return sb.toString();
	  }


	/**
	 * InputStream 으로 들어온 data를 buffer에 넣었다가 string으로 변환하여 return 한다.
	 *
	 * @param in
	 *            InputStream
	 * @return String
	 * @throws Exception
	 */
	public static String getStringFromInputStream(InputStream in) {
		StringBuffer buff = null;
		BufferedReader read = null;
		String line = "";

		try {

			// InputStream의 자료를 담기 위해 StringBuffer객체 선언
			buff = new StringBuffer();
			read = new BufferedReader(new InputStreamReader(in,"EUC-KR"));

			while ((line = read.readLine()) != null) {
				buff.append(line + "\n");
			}

			// BufferedReader 가 null 이 아니면 BufferedReader를 close 시키다.
			if (read != null) {
				read.close();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return buff.toString();
	}

	public static String gvq(int cnt){
		String str = "";
		for(int i=0; i<cnt; i++){
			str += "?,";
		}
		return str.substring(0, str.length()-1);
	}

	public static String[] ExecFileExtensions=new String[]{
		".ADE"
		,".ASP"
		,".JSP"
		,".PHP"
		,".PHP3"
		,".ASPX"
		,".CGI"
		,".HTM"
		,".HTML"
		,".SHTML"
		,".SHTM"
		,".ADP"
		,".BAS"
		,".BAT"
		,".CHM"
		,".CMD"
		,".COM"
		,".CPL"
		,".CRT"
		,".DLL"
		//,".DO*"
		,".EXE"
		,".HLP"
		,".HTA"
		,".INF"
		,".INS"
		,".ISP"
		,".JS"
		,".JSE"
		,".LNK"
		,".MDB"
		,".MDE"
		,".MSC"
		,".MSI"
		,".MSP"
		,".MST"
		,".OCX"
		,".PCD"
		,".PIF"
		,".POT"
		//,".PPT"
		,".REG"
		,".SCR"
		,".SCT"
		,".SHB"
		,".SHS"
		,".SYS"
		,".URL"
		,".VB "
		,".VBE"
		,".VBS"
		,".WSC"
		,".WSF"
		,".WSH"
		//,".XL*"

	};
	public static boolean isExecutableFile(String fileName){
		if(fileName==null || fileName.trim().length()==0) return false;

		fileName=fileName.toUpperCase();

		for(int i=0;i<StringUtil.ExecFileExtensions.length;i++)
			if(fileName.endsWith(StringUtil.ExecFileExtensions[i])) return true;
		return false;
	}


	//2007.07.04 천우성 추가
	public static java.sql.Timestamp getAddDate(char gb, int v) {
	      java.sql.Timestamp t = new java.sql.Timestamp(new Date().getTime());
	      t.setHours(0);
	      t.setMinutes(0);
	      t.setSeconds(0);

	      return getAddDate(t,gb,v);
	}
	//2007.07.04 천우성 추가
	public static java.sql.Timestamp getAddDate(java.sql.Timestamp d, char gb, int v) {
	      Calendar c = Calendar.getInstance();

	      c.set(Calendar.YEAR, (d.getYear()+1900));
	      c.set(Calendar.MONTH, (d.getMonth()+1));
	      c.set(Calendar.DATE, d.getDate());
	      switch(gb){
	      	case 'Y': 	case 'y':
	      		c.add(Calendar.YEAR, v);
	      		break;
	      	case 'M':	case 'm':
	      		c.add(Calendar.MONTH,(v-1));
	      		break;
	      	case 'D':	case 'd':
	      		c.add(Calendar.DATE, v);
	      		break;
	      }

	      return new java.sql.Timestamp(c.getTime().getTime());
	}

	//2007.07.16 천우성- 이정주대리가 만들어 놓은 format-util.jsp 옮겨놓은것
	public static String formatNumber( Object o, int scale ){
		if( o==null )
			return "";

		BigDecimal num = null;
		if(o instanceof String ) {
			num = parseBigDecimal((String)o);
		}
		else
			num=(BigDecimal)o;

		if(num==null) return "";

		String formatStr = "#,##0";
		if(scale>0)
			formatStr += "."+("00000000000000000".substring(0,scale));

		return format( formatStr, num );
	}

//	 format amount.
	public static String formatAmount( boolean isKRW, Object o ){
		if( o==null )
			return "";

		BigDecimal num = null;
		if(o instanceof String ) {
			num = parseBigDecimal((String)o);
		}
		else
			num=(BigDecimal)o;

		return num==null?"":format( isKRW?"#,##0":"#,##0.00", num );
	}

//	 format float amount.
	public static String formatFloatAmount( Object o ){
		if( o==null )
			return "";

		BigDecimal num = null;
		if(o instanceof String ) {
			num = parseBigDecimal((String)o);
		}
		else
			num=(BigDecimal)o;

		return num==null?"0":format("###0.00", num );
	}

//	 format count
	public static String formatCount( Object o ){
		if( o==null )
			return "";

		BigDecimal num = null;
		if(o instanceof String ) {
			num = parseBigDecimal((String)o);
		}
		else
			num=(BigDecimal)o;

		return num==null?"":format( "#,##0.###", num );
	}

	public static String formatDate( Object o ){
		if(o==null)
			return "";

		if(o instanceof String ) {
			if(((String)o).indexOf('.')>=0 ||((String)o).indexOf('-')>=0||((String)o).indexOf('/')>=0)
				o = parseDate((String)o);
			else
				o = toDateFromYYYYMMDD((String)o);
		}

		return format("yyyy.MM.dd", (Date)o);
	}

	public static String formatTime( Object o ){
		if(o==null)
			return "";

		if(o instanceof String ) {
			if(((String)o).indexOf('.')>=0 ||((String)o).indexOf('-')>=0||((String)o).indexOf('/')>=0)
				o = parseDate((String)o);
			else
				o = toDateFromYYYYMMDD((String)o);
		}

		return format("yyyy.MM.dd HH:mm", (Date)o);
	}

	public static String formatTimeOnly( Object o ){
		if(o==null)
			return "";
		if(o instanceof String )
			o = parseDate((String)o);

		return format("HH:mm", (Date)o);
	}

	 public static String[] str = {"<html", "</html", "<meta", "<link", "<head", "</head",
	    	"<body", "</body", "<form", "</form", "<script", "</script",
	    	"<style", "</style", "script:", "cookie", "document."};

    public static boolean checkRequest(String params[]){

    	for(int x=0; x<params.length; x++){
    		String param = StringUtil.nvl(params[x]);
    		System.out.println(param);
    		for(int i=0; i < str.length; i++){
    			if(param.indexOf(str[i]) > 0){
    				return false;
    			}
    		}
    	}
        return true;
    }

    public static boolean checkDateTime(String datetime){
    	boolean isCheck = false;

    	if(datetime==null){
    		return false;
    	}else{
    		Date baseDate = parseDate("1900-01-01 00:00");
    		Date d = parseDate(datetime);

    		isCheck = d.after(baseDate);
    	}

    	return isCheck;
    }

	/**
	 * 엑셀 출력에 안전한 문자열 반환( ex. 한글-> &#xxx; 식으로 변환
	 *
	 * @param str 문자열
	 * @return 인코드된 문자열
	 */
	public static String encExcel(Object str) {
		StringBuffer ss = new StringBuffer();
		String s = htmlEncode(str);
		int l = s.length();
		for (int i = 0; i < l; i++) {
			char c = s.charAt(i);

			if (c > 127) {
				ss.append("&#" + ((int) c) + ";");
			} else {
				ss.append(c);
			}
		}
		return ss.toString();
	}

	/**
	 * 자바스크립트로 escape된 문자열을 unescape함
	 * 정해원
	 * @param inp
	 * @return
	 */
	public static String unescape(String inp) {
	    String rtnStr = new String();
	    char [] arrInp = inp.toCharArray();
	    int i;
	    for(i=0;i<arrInp.length;i++) {
	        if(arrInp[i] == '%') {
	            String hex;
	            if(arrInp[i+1] == 'u') {    //유니코드.
	                hex = inp.substring(i+2, i+6);
	                 i += 5;
	             } else {    //ascii
	                 hex = inp.substring(i+1, i+3);
	                 i += 2;
	             }
	             try{
	                 rtnStr += new String(Character.toChars(Integer.parseInt(hex, 16)));
	             } catch(NumberFormatException e) {
	                 rtnStr += "%";
	                 i -= (hex.length()>2 ? 5 : 2);
	             }
	         } else {
	             rtnStr += arrInp[i];
	         }
	     }

	     return rtnStr;
	}

    /**
     * input 항목을 찾는 정규표현식 패턴
     */
    static Pattern P1 = Pattern.compile(
            // input 항목을 찾는다
            "\\<input[^\\>]+name\\=([a-zA-Z0-9_]+)[^\\>]*\\>",
            Pattern.CASE_INSENSITIVE);

    /**
     * value 값을 찾는 정규표현식 패턴
     */
    static Pattern P2 = Pattern.compile( // value 항목을 찾는다
            "value\\s*\\=\\s*([^\\s\\>]+)", Pattern.CASE_INSENSITIVE);

    /**
     * input 항목을 찾는 정규표현식 패턴
     */
    static Pattern P3 = Pattern.compile(
            // textarea 항목을 찾는다
            "\\<TEXTAREA[^\\>]+name\\=([a-zA-Z0-9_]+)[^\\>]*\\></TEXTAREA>",
            Pattern.CASE_INSENSITIVE);

    /**
     * s 에 s1에 해당하는 input 의 값을 s2로 바꿔준다.
     *
     * @param s 원문
     * @param s1 찾을 문자열
     * @param s2 바꿀 문자열
     * @param readOnly 속성
     * @param changeType input value replace
     * @return String
     */
    public static String setNewInfoContForm(String s, String s1, String s2, boolean readOnly, boolean changeType) {
        StringBuffer sb = new StringBuffer();
        int i = 0;

        Matcher m = P1.matcher(s);

        while(m.find()){
        	sb.append(s.substring(i, m.start(0)));
        	String nm 	= m.group(1);
        	String all 	= m.group(0);

        	if(s1.equals(nm)){
        		if(!changeType){
        			StringBuffer sb2 = new StringBuffer();
                    int j = 0;

                    Matcher m2 = P2.matcher(all);
                    while(m2.find(j)){
                    	sb2.append(s.substring(j, m2.start(0)));
                    	String nm2 = m2.group(1);
                    	if(all.equals(nm2)){
                    		sb2.append("value=\"").append(s2).append("\" ");
                    	}else{
                    		sb2.append(m2.group(0));
                    	}

                    	j = m2.end(0);
                    }
                    if( j== 0 ){
                    	sb2.append(all);
                    }else{
                    	sb2 = sb2.append(all.substring(j));
                    }

                    String returnVal = "";
                    if(!readOnly){
                    	returnVal = StringUtil.replace(sb2.toString(), "readOnly", "");
                    }else{
                    	returnVal = sb2.toString();
                    }
                    sb.append(returnVal);
        		}else{
        			sb.append(s2);
        		}
        	}else{
        		sb.append(m.group(0));
        	}

        	i = m.end(0);
        }

        if( i == 0 ){
        	sb.append(s);
        }else{
        	sb = sb.append(s.substring(i));
        }

        return sb.toString();
    }

    /**
     * s에 s1에 해당하는 input의 값을 s2[]로 바꿔준다.
     *
     * @param s 원문
     * @param s1 찾을 문자열
     * @param s2 바꿀 문자열(배열)
     * @param readOnly 속성
     * @param changeType input value replace
     * @return String
     */
    public static String setNewInfoContForm(String s, String s1, String s2[],  boolean readOnly, boolean changeType) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int p = 0;

        Matcher m = P1.matcher(s);

        while (m.find(i)) {
        	sb.append(s.substring(i, m.start(0)));
        	String nm 	= m.group(1);
        	String all 	= m.group(0);

            if (s1.equals(nm)) {
                if (!changeType) {
                    StringBuffer sb2 = new StringBuffer();
                    int j = 0;

                    Matcher m2 = P2.matcher(all);
                    while(m2.find(j)){
                    	sb2.append(s.substring(j, m2.start(0)));
                    	String nm2 = m2.group(1);
                    	if(all.equals(nm2)){
                    		sb2.append("value=\"").append(s2[p]).append("\" ");
                    	}else{
                    		sb2.append(m2.group(0));
                    	}

                    	j = m2.end(0);
                    }

                    if( j== 0 ){
                    	sb2.append(all);
                    }else{
                    	sb2 = sb2.append(all.substring(j));
                    }

                    String returnVal = "";
                    if (!readOnly) {
                        returnVal = StringUtil.replace(sb2.toString(), "readOnly", "");
                    } else {
                        returnVal = sb2.toString();
                    }
                    sb.append(returnVal);
                } else {
                    sb.append(s2[p]);
                    p++;
                }
            } else {
                sb.append(m.group(0));
            }

            i = m.end(0);
        }
        if (i == 0)
            sb.append(s);
        else {
            sb = sb.append(s.substring(i));
        }

        return sb.toString();
    }

    /**
     * s에 s1에 해당하는 input의 값을 s2[]로 바꿔준다.
     *
     * @param s 원문
     * @param s1 찾을 문자열
     * @param s2 바꿀 문자열(배열)
     * @param readOnly 속성
     * @param changeType input value replace
     * @return String
     */
    public static String setNewInfoContFormText(String s, String s1, String s2[], boolean readOnly, boolean changeType) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int p = 0;

        Matcher m = P3.matcher(s);

        while (m.find(i)) {
            sb.append(s.substring(i, m.start(0)));
            String nm = m.group(1);
            if (s1.equals(nm)) {
                sb.append(s2[p]);
                p++;
            } else {
                sb.append(m.group(0));
            }

            i = m.end(0);
        }
        if (i == 0)
            sb.append(s);
        else {
            sb = sb.append(s.substring(i));
        }
        return sb.toString();
    }
}