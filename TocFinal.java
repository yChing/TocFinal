import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.*;

public class TocFinal {
	static ArrayList<String> keys = new ArrayList<String>();
	static HashMap<String, Value> hashMap = new HashMap<String, Value>();
	static JSONObject publicJSONObject;
	static int appearTime = 0;
	static StringBuilder publicStringBuilder = new StringBuilder();


	static int K;
	static int L;
	public static void processFromPage(String httpAddress) {

		URL u = null;
		InputStream in = null;
		InputStreamReader r = null;
		BufferedReader br = null;

		try {

			u = new URL(httpAddress);
			in = u.openStream();

			r = new InputStreamReader(in, "UTF-8");
			br = new BufferedReader(r);

			String buffer = null;

			while ((buffer = br.readLine()) != null) {
				try {
					if (keys.size() == 0)
						keys = findOrder(buffer);
					if (buffer.charAt(0) == '{') {
						publicJSONObject = new JSONObject(new String(buffer));
						combination(L, 0, 0, "");
					}
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				u = null;
				in.close();
				r.close();
				br.close();
			} catch (Exception e) {

			}

		}
	}

	public static void processFromFile(String filename) {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			try {
				do {
					String buffer = reader.readLine();
					if (buffer == null)
						break;

					try {
						if (keys.size() == 0)
							keys = findOrder(buffer);
						if (buffer.charAt(0) == '{') {
							publicJSONObject = new JSONObject(
									new String(buffer));
							combination(L, 0, 0, "");
						}
					} catch (Exception e) {
					}

				} while (true);
			} catch (Exception e) {
			} finally {
				reader.close();
			}
		} catch (Exception e) {
		}
	}



	public static void main(String[] args) throws MalformedURLException,
			JSONException, FileNotFoundException {

		String URL = args[0];

		K = Integer.valueOf(args[1]);

		L = Integer.valueOf(args[2]);
		if (isURL(URL))
			processFromPage(URL);
		else {
			processFromFile(URL);
		}
		try {
			outputReslut();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}