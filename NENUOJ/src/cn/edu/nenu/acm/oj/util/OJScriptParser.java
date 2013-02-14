package cn.edu.nenu.acm.oj.util;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.nenu.acm.oj.eto.InvalidScript;

public class OJScriptParser {

	private JSONObject script;
	private HashMap<String, Object> variable;

	public OJScriptParser(String source) throws InvalidScript {
		try {
			script = new JSONObject(source);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new InvalidScript("Invalid JSON source.");
		}
		String[] requireModule = { "variable", "crawler", "submitter" };
		for (String module : requireModule)
			if (!script.has(module))
				throw new InvalidScript("[" + module + "] module is require.");
		JSONObject variable = null;
		try {
			variable = script.getJSONObject("variable");
			this.variable = new HashMap<String, Object>();
			for (String name : JSONObject.getNames(variable)) {
				this.variable.put(name, variable.get(name));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new InvalidScript("Preasing variable, error occurs.");
		}
	}

	public void setParameter(String key,Object value){
		variable.put(key, value);
	}
	
	public void crawle(){
		
	}
	
	public void submit(){
		
	}
}
