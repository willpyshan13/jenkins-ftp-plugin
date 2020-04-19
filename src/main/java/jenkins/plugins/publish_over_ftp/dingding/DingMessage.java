package jenkins.plugins.publish_over_ftp.dingding;

import com.alibaba.fastjson.JSONObject;
import hidden.jth.org.apache.http.client.methods.CloseableHttpResponse;
import hidden.jth.org.apache.http.client.methods.HttpPost;
import hidden.jth.org.apache.http.entity.StringEntity;
import hidden.jth.org.apache.http.impl.client.CloseableHttpClient;
import hidden.jth.org.apache.http.impl.client.HttpClientBuilder;
import hidden.jth.org.apache.http.util.TextUtils;
import jenkins.plugins.publish_over_ftp.BapFtpClient;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DingMessage {

    SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final Log logger = LogFactory.getLog(BapFtpClient.class);
    private static final String apiUrl = "https://oapi.dingtalk.com/robot/send?access_token=";

    private static String api;

    /**发送到钉钉消息
     *
     * @param content
     * @param token
     * @param phone
     * @param isAll
     * @return
     */
    public static Map<String, Object> sendText(String content, String token, String phone, boolean isAll) {
        String url = apiUrl + token;
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json; charset=utf-8");

        JSONObject bodys = new JSONObject();
        bodys.put("msgtype", "text");

        JSONObject text = new JSONObject();
        text.put("content", "\n" + content);
        bodys.put("text", text);

        AtMobiles atMobiles = new AtMobiles();
        if (!StringUtils.isEmpty(phone)) {
            atMobiles.setAtMobiles(Arrays.asList(phone.split(",")));
        }
        atMobiles.setIsAtAll(isAll);
        bodys.put("at", atMobiles);
        System.out.println(bodys.toJSONString());
        StringEntity se = new StringEntity(bodys.toJSONString(), "utf-8");
        httpPost.setEntity(se);
        CloseableHttpResponse execute = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            execute = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        int statusCode = execute.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        map.put("code", statusCode);
        if (statusCode == 200) {
            map.put("msg", "send success");
        } else if (statusCode == 302) {
            map.put("msg", "Illegal token");
        } else {
            map.put("msg", "error");
        }
        return map;
    }

    /**
     * 发送钉钉消息
     * @param version
     * @param token
     * @param updateLog
     * @param dingPerson
     * @param platform
     * @param appToken
     */
    public void sendMultiMessage(String version, String token, String updateLog, String dingPerson, String platform, String appToken) {
        if (!TextUtils.isEmpty(token)&&token.contains(",")){
            String[] tokens = token.split(",");
            for (int i = 0;i< tokens.length;i++){
                sendTextMessage(version,tokens[i],updateLog,dingPerson,platform,appToken);
            }
        }else if(!TextUtils.isEmpty(token)){
            sendTextMessage(version,token,updateLog,dingPerson,platform,appToken);
        }
    }

        /**
         * 发送钉钉消息
         * @param version
         * @param token
         * @param updateLog
         * @param dingPerson
         * @param platform
         * @param appToken
         */
    public void sendTextMessage(String version, String token, String updateLog, String dingPerson, String platform, String appToken) {
        api = apiUrl + token;
        JSONObject body = new JSONObject();
        body.put("msgtype", "markdown");
        body.put("isAtAll", false);

        JSONObject contentObject = new JSONObject();
        MessageMarkdown messageMarkdown = new MessageMarkdown();
        messageMarkdown.content = "[" + platform + "]" + "\n" +
                "下载地址\nhttps://app.vvtechnology.cn:8024/dist/android/download/download.html?token=" + appToken + "\n"
                + "版本：" + version + "\n"
                + "时间：" + smf.format(System.currentTimeMillis()) + "\n" +
                "更新内容：" + updateLog;

        contentObject.put("content", messageMarkdown.content);
        body.put("text", contentObject);
        sendText(messageMarkdown.content,token,dingPerson,false);
    }

    class MessageMarkdown {
        String title;
        String content;
    }

}
