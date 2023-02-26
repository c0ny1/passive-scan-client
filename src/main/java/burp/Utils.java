package burp;

import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Utils {

    private static final Object FLAG_EXIST = "y";

    public static String MD5(String key) {
        //import java.security.MessageDigest;
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = key.getBytes();
            //获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            mdInst.update(btInput);
            //获得密文
            byte[] md = mdInst.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getBanner(){
        String bannerInfo =
                "[+] " + BurpExtender.extensionName + " is loaded\n"
                        + "[+] #####################################\n"
                        + "[+]    " + BurpExtender.extensionName + " v" + BurpExtender.version +"\n"
                        + "[+]    anthor: c0ny1\n"
                        + "[+]    github: https://github.com/c0ny1/passive-scan-client\n"
                        + "[+]    update: https://github.com/winezer0/passive-scan-client-plus\n"
                        + "[+] ####################################";
        return bannerInfo;
    }

    public static void updateSuccessCount(){
        synchronized(Config.FAIL_TOTAL){
            Config.REQUEST_TOTAL++;
            Config.SUCCESS_TOTAL++;
            GUI.lbRequestCount.setText(String.valueOf(Config.REQUEST_TOTAL));
            GUI.lbSuccessCount.setText(String.valueOf(Config.SUCCESS_TOTAL));
        }
    }

    public static void updateFailCount(){
        synchronized(Config.SUCCESS_TOTAL){
            Config.REQUEST_TOTAL++;
            Config.FAIL_TOTAL++;
            GUI.lbRequestCount.setText(String.valueOf(Config.REQUEST_TOTAL));
            GUI.lbFailCount.setText(String.valueOf(Config.FAIL_TOTAL));
        }
    }

    public static void showStderrMsg(Integer msgLevel,String msg){
        if(msgLevel <=  Config.SHOW_MSG_LEVEL){
            BurpExtender.stderr.println(msg);
        }
    }

    public static void showStdoutMsg(Integer msgLevel, String msg){
        if(msgLevel <=  Config.SHOW_MSG_LEVEL){
            BurpExtender.stdout.println(msg);
        }
    }

    //完全关键字匹配正则
    public static boolean isEqualKeywords(String regx, String str, Boolean NoRegxValue){
        //如果没有正在表达式,的情况下返回指定值 NoRegxValue
        if (regx.trim().length() == 0){
            return NoRegxValue;
        }

        Pattern pat = Pattern.compile("^("+regx+")$",Pattern.CASE_INSENSITIVE);//正则判断
        Matcher mc= pat.matcher(str);//条件匹配
        return mc.find();
    }

    //包含关键字匹配正则
    public static boolean isMatchKeywords(String regx, String str, Boolean NoRegxValue){
        //如果没有正在表达式,的情况下返回指定值 NoRegxValue
        if (regx.trim().length() == 0){
            return NoRegxValue;
        }

        Pattern pat = Pattern.compile("^.*("+regx+").*$",Pattern.CASE_INSENSITIVE);//正则判断
        Matcher mc= pat.matcher(str);//条件匹配
        return mc.find();
    }

    //域名匹配
    public static boolean isMatchTargetHost(String regx, String str, Boolean NoRegxValue){
        return isMatchKeywords(regx, str, NoRegxValue);
    }

    //域名匹配
    public static boolean isMatchBlackHost(String regx, String str, Boolean NoRegxValue){
        //如果没有正则表达式,的情况下返回指定值 NoRegxValue
        if (regx.trim().length() == 0){
            return NoRegxValue;
        }

        Pattern pat = Pattern.compile("^.*("+regx+")$",Pattern.CASE_INSENSITIVE);//正则判断
        Matcher mc= pat.matcher(str);//条件匹配
        return mc.find();
    }

    //获取请求路径的扩展名
    public static String getPathExtension(String path) {
        String extension="";

        if("/".equals(path)||"".equals(path)){
            return extension;
        }

        try {
            String[] pathContents = path.split("[\\\\/]");
            int pathContentsLength = pathContents.length;
            String lastPart = pathContents[pathContentsLength-1];
            String[] lastPartContents = lastPart.split("\\.");
            if(lastPartContents.length > 1){
                int lastPartContentLength = lastPartContents.length;
                //extension
                extension = lastPartContents[lastPartContentLength -1];
            }
        }catch (Exception exception){
            Utils.showStderrMsg(2, String.format("[*] GetPathExtension [%s] Occur Error [%s]", path, exception.getMessage()));
        }
        //BurpExtender.out.println("Extension: " + extension);
        return extension;
    }

    //后缀匹配
    public static boolean isMatchBlackSuffix(String regx, String path, Boolean NoRegxValue){
        //如果没有正在表达式,的情况下返回指定值 NoRegxValue
        if (regx.trim().length() == 0){
            return NoRegxValue;
        }

        String ext = getPathExtension(path);
        //无后缀情况全部放行
        if("".equalsIgnoreCase(ext)){
            return false;
        }else {
            //Pattern pat = Pattern.compile("([\\w]+[\\.]|)("+regx+")",Pattern.CASE_INSENSITIVE);//正则判断
            Pattern pat = Pattern.compile("^("+regx+")$",Pattern.CASE_INSENSITIVE);//正则判断
            Matcher mc= pat.matcher(ext);//条件匹配
            return mc.find();
        }
    }

    //判断字符串是否为空
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    //判断字符串是否是Json格式
    public static boolean isJson(Object obj) {
        try{
            String str = obj.toString().trim();

            if (str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') {
                JSONObject.parseObject(str);
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    //获取请求信息(URL和Body)的HASH
    public static String calcReqInfoHash(String reqUrl, byte[] reqBody ) {
        String reqInfoHash = "";
        try {
            if(reqBody == null){
                reqInfoHash = reqUrl;
            }else {
                reqInfoHash = reqUrl + "&" + Utils.MD5(Arrays.toString(reqBody));
            }
        }catch (Exception exception){
            Utils.showStderrMsg(2, String.format("[*] getReqInfoHash [%s] Occur Error [%s]", reqUrl, exception.getMessage()));
        }
        return reqInfoHash;
    }

    //获取所有请求参数的JSON格式字符串
    public static String IParametersToJsonStr(List<IParameter> reqParams, Boolean useValue) {
        JSONObject reqParamsJson = new JSONObject();
        for (IParameter param:reqParams) {
            if(useValue){
                //默认按照值内容组成参数键值对,该方式比较字符串会更耗时
                reqParamsJson.put(param.getName(), param.getValue());
            }else {
                //当前根据是否存在值,该方式比较字符串会省一点时间
                reqParamsJson.put(param.getName(), FLAG_EXIST);
            }
            //后续可能考虑 多种参数情况,处理起来会比较耗时 如有无参数[false,true] 参数类型[None,int,string,bytes等]
        }
        //排序输出URL JSON
        String reqParamsJsonStr = JSON.toJSONString(reqParamsJson, SerializerFeature.MapSortField);
        return reqParamsJsonStr;
    }

    //判断Json是否是不重复的(不存在于HashMap中),独特返回true,重复返回false
    public static Boolean isUniqReqInfo(HashMap<String,String> reqInfoHashMap, String reqUrl, String newReqParamsJsonStr, Boolean useValue) {
        //判断全局HashMap里面是否已经存在URL对应的参数字符串，有就合并新旧参数字符串，没有就直接存入
        String oldReqParamsJsonStr = reqInfoHashMap.get(reqUrl);
        showStderrMsg(2, String.format("[*] Old ReqParamsJsonStr:%s", oldReqParamsJsonStr));
        showStderrMsg(2, String.format("[*] New ReqParamsJsonStr:%s", newReqParamsJsonStr));

        //不存在历史参数列表，直接存入,返回false
        if(Utils.isEmpty(oldReqParamsJsonStr)){
            reqInfoHashMap.put(reqUrl, newReqParamsJsonStr);
            Utils.showStdoutMsg(1, String.format("[+] reqInfoHashMap Add By None:%s", reqInfoHashMap.get(reqUrl)));
            return true;
        }

        //如果新旧的参数JsonStr相同,直接返回false
        if(newReqParamsJsonStr.equals(oldReqParamsJsonStr)){
            return false;
        }

        //如果对应请求目标已经存在参数Json,且不完全相同,需要解开参数JsonStr进行对比
        JSONObject oldReqParamsJsonObj = JSONObject.parseObject(oldReqParamsJsonStr);

        //如果旧的JsonStr内没有数据,就直接存入新的参数JsonStr //大概是不会到达这个情况
        if(oldReqParamsJsonObj == null){
            reqInfoHashMap.put(reqUrl, newReqParamsJsonStr);
            Utils.showStdoutMsg(1, String.format("[+] reqInfoHashMap Add By Null:%s",reqInfoHashMap.get(reqUrl) ));
            return true;
        }

        //hasNewParam 记录是否存在新的参数
        boolean hasNewParam = false;
        //解析新的json参数对象,并进行便利
        Map<String, String> newReqParamsJsonMap = JSONObject.parseObject(newReqParamsJsonStr, Map.class);
        for(Map.Entry<String, String> newReqParamEntry : newReqParamsJsonMap.entrySet()){
            //判断旧的参数对象是否包含新的参数key，否则跳过，
            if(oldReqParamsJsonObj.containsKey(newReqParamEntry.getKey())){
                continue;
            }
            //是则往旧的Json参数对象 存入 新的参数
            if(useValue){
                oldReqParamsJsonObj.put(newReqParamEntry.getKey(), newReqParamEntry.getValue());
            }else {
                oldReqParamsJsonObj.put(newReqParamEntry.getKey(), FLAG_EXIST);
            }
            hasNewParam = true;
        }

        //如果有新参数加入就重新整理HashMap
        if(hasNewParam){
            //将新的参数Json对象转换后放入HASHMAP集合
            reqInfoHashMap.put(reqUrl, oldReqParamsJsonObj.toJSONString());
            Utils.showStdoutMsg(1, String.format("[+] reqInfoHashMap Add By New:%s", reqInfoHashMap.get(reqUrl)));
            return true;
        }else {
            return false;
        }
    }

    //根据 hashmap格式的请求参数:值 键值对 ,获取参数对应的参数Json
    public static String paramsHashMapToJsonStr(HashMap<String,String> paramsHashMap, Boolean useValue) {
        //组合成参数json
        JSONObject reqParamsJson = new JSONObject();
        for(String param : paramsHashMap.keySet()) {
            if(useValue){
                reqParamsJson.put(param, paramsHashMap.get(param));
            }else {
                reqParamsJson.put(param, FLAG_EXIST);
            }
        }

        //排序输出URL的参数JSONStr,防止相同参数不同顺序导致的判断错误.
        String reqParamsJsonStr = JSON.toJSONString(reqParamsJson, SerializerFeature.MapSortField);
        return reqParamsJsonStr;
    }

    //处理JSon格式的参数字符串，获取 参数:值 键值对 hashmap
    public static HashMap JsonParamsToHashMap(String ParamsStr, Boolean useValue) {
        HashMap paramHashMap = new HashMap<>();
        Map<String, String> map = JSONObject.parseObject(ParamsStr, Map.class);
        for(Map.Entry<String, String> obj : map.entrySet()){
            String tempKey = obj.getKey();
            ParamValueHandle(useValue, paramHashMap, obj, tempKey);
        }
        return paramHashMap;
    }

    //递归处理Json内的子Json
    public static HashMap subJsonStrToHashMap(String prefix, String subParamsStr, Boolean useValue){
        HashMap subParamHashMap = new HashMap();
        Map<String, String> subMap = JSONObject.parseObject(subParamsStr, Map.class);
        for(Map.Entry<String, String> subObj : subMap.entrySet()) {
            String tempKey = String.format("%s.%s", prefix, subObj.getKey());
            ParamValueHandle(useValue, subParamHashMap, subObj, tempKey);
        }
        return subParamHashMap;
    }

    public static void ParamValueHandle(Boolean useValue, HashMap subParamHashMap, Map.Entry<String, String> subObj, String tempKey) {
        String tempValue = subObj.getValue();
        String tempValueUrlDecode = decodeUrl(tempValue);
        if(isJson(tempValueUrlDecode)){
            HashMap subSubParamsHashMap = subJsonStrToHashMap(tempKey, tempValueUrlDecode, useValue);
            subParamHashMap.putAll(subSubParamsHashMap);
        }else {
            if(useValue){
                subParamHashMap.put(tempKey, tempValue);
            }else {
                subParamHashMap.put(tempKey, FLAG_EXIST);
            }
        }
    }

    //往人工解析json请求体得到的hashmap内添加 burp 自动解析出来的 参数:值对
    public static HashMap ParamsHashMapAddIParams(HashMap<String,String> paramsHashMap, List<IParameter> parameters) {
        for (IParameter param:parameters) {
            paramsHashMap.put(param.getName(), param.getValue());
        }
        return paramsHashMap;
    }

    //计算字符串内是否至少包含limit个指定字符 //只需要处理多层级别的Json就可以了,单层的用内置方法即可
    public static boolean countStr(String longStr, String mixStr, int limit, boolean decode) {
        //进行URL解码
        if (decode) {
            longStr = decodeUrl(longStr);
        }

        int count = 0;
        int index = 0;
        while((index = longStr.indexOf(mixStr,index))!= -1){
            index = index + mixStr.length();
            count++;
            if(count >= limit){
                return true;
            }
        }
        return false;
    }

    //获取参数列表里面的认证信息字符串
    public static HashMap ExtractIParamsAuthParam(List<IParameter> parameters,List<String> headers, Boolean addHeaderAuth) {
        HashMap authParamsHashMap = new HashMap<>();

        //常见的情况2,Cookie中的SESSION ID|JSEESION|PHPSSIONID| |user.id=
        //当前输入的是参数列表
        for (IParameter param:parameters) {
            if(isMatchKeywords(Config.AUTH_INFO_REGX, param.getName(), false)){ //关键字正则匹配
                authParamsHashMap.put(param.getName(), param.getValue());
                //showStdoutMsgDebug(String.format("[*] Auth Param: %s --- %s",param.getName(), param.getValue() ));
            }
        }

        //常见的情况1,AUTH头、Token头、
        if(addHeaderAuth){
            for (String header : headers) {
                List<String> headerInfo = Arrays.asList(header.split(": ", 2));
                if (headerInfo.size() == 2){
                    String headerName = headerInfo.get(0);
                    String headerValue = headerInfo.get(1);
                    if(isMatchKeywords(Config.AUTH_INFO_REGX, headerName, false)){ //关键字正则匹配
                        authParamsHashMap.put(headerName, headerValue);
                        //showStdoutMsgDebug(String.format("[*] Auth Header: %s --- %s",headerName, headerValue));
                    }
                }
            }
        }
        return authParamsHashMap;
    }

    //判断参数值是否含有Json //需要URL解码
    public static Boolean paramValueHasJson(List<IParameter> parameters){
        for (IParameter param:parameters) {
            String tempValue = param.getValue();
            String tempValueUrlDecode = decodeUrl(tempValue);
            if(isJson(tempValueUrlDecode)){
                return true;
            }
        }
        return false;
    }

    //进行URL解码
    public static String decodeUrl(String str) {
        if (!isEmpty(str)) {
            for (int i = 0; i < Config.DECODE_MAX_TIMES; i++) {
                if (str.contains("%")) {
                    str = BurpExtender.helpers.urlDecode(str);
                }else {
                    break;
                }
            }
        }
        return str;
    }

    //获取所有请求参数的JSON格式字符串//支持处理参数值为Json的格式
    public static String IParametersToJsonStrPlus(List<IParameter> reqParams, Boolean useValue) {
        JSONObject reqParamsJson = new JSONObject();
        for (IParameter param:reqParams) {
            String tempKey = param.getName();
            String tempValue = param.getValue();
            String tempValueUrlDecode = decodeUrl(tempValue);

            if(isJson(tempValueUrlDecode)){
                HashMap subParamsHashMap = subJsonStrToHashMap(tempKey, tempValueUrlDecode, useValue);
                reqParamsJson.putAll(subParamsHashMap);
            }else{
                if(useValue){
                    //默认按照值内容组成参数键值对,该方式比较字符串会更耗时
                    reqParamsJson.put(tempKey, param.getValue());
                }else {
                    //当前根据是否存在值,该方式比较字符串会省一点时间
                    reqParamsJson.put(tempKey, FLAG_EXIST);
                }
            }
        }
        //排序输出URL JSON
        String reqParamsJsonStr = JSON.toJSONString(reqParamsJson, SerializerFeature.MapSortField);
        return reqParamsJsonStr;
    }
}
