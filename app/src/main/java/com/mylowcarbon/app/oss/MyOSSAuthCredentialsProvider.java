package com.alibaba.sdk.android.oss.common.auth;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.OSSConstants;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jingdan on 2017/11/15.
 * Authentication server issued under the agreement of the official website agreement, you can directly use the provider
 */

public class MyOSSAuthCredentialsProvider extends OSSFederationCredentialProvider {

    private String mAuthServerUrl;
    private AuthDecoder mDecoder;

    public MyOSSAuthCredentialsProvider(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set auth server url
     *
     * @param authServerUrl
     */
    public void setAuthServerUrl(String authServerUrl) {
        this.mAuthServerUrl = authServerUrl;
    }

    /**
     * set response data decoder
     *
     * @param decoder
     */
    public void setDecoder(AuthDecoder decoder) {
        this.mDecoder = decoder;
    }

    @Override
    public OSSFederationToken getFederationToken() throws ClientException {

        OSSFederationToken authToken;
        String authData;
        try {

            URL stsUrl = new URL(mAuthServerUrl);
            HttpURLConnection conn = (HttpURLConnection) stsUrl.openConnection();
            conn.setRequestProperty("app-key", "pZzHfOq3fhlv57FsH2Hk5h2LKNS33s35");
            Log.e("test","*************************OSSFederationToken  22");

            conn.setConnectTimeout(10000);
            InputStream input = conn.getInputStream();
            Log.e("test","*************************OSSFederationToken  33");

            authData = IOUtils.readStreamAsString(input, OSSConstants.DEFAULT_CHARSET_NAME);
            if (mDecoder != null) {
                authData = mDecoder.decode(authData);
            }
            Log.e("test","*************************OSSFederationToken  44");

            JSONObject jsonObj = new JSONObject(authData);
            Log.e("test",""+jsonObj.toString());
            int statusCode = jsonObj.getInt("code");
            if (statusCode == 200) {
                String ak = jsonObj.getString("AccessKeyId");
                String sk = jsonObj.getString("AccessKeySecret");
                String token = jsonObj.getString("SecurityToken");
                String expiration = jsonObj.getString("Expiration");
                authToken = new OSSFederationToken(ak, sk, token, expiration);
            } else {
                String errorCode = jsonObj.getString("ErrorCode");
                String errorMessage = jsonObj.getString("ErrorMessage");
                throw new ClientException("ErrorCode: " + errorCode + "| ErrorMessage: " + errorMessage);
            }
            return authToken;
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }

    public interface AuthDecoder {
        String decode(String data);
    }
}
