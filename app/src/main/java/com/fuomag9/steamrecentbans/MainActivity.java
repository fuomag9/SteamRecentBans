package com.fuomag9.steamrecentbans;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        WebView myWebView = (WebView) findViewById(R.id.WebView);   //webview viene setuppato all'apertura dell'app così permette di
        myWebView.getSettings().setJavaScriptEnabled(true);         // handlare anche gli intent di condivisione direttamente
        //myWebView.setWebChromeClient(new WebChromeClient());     //idk if this is useful
        CookieSyncManager.createInstance(this); //questo veniva usato su internet ma a me funziona pure senza, lascio qui che non si sa mai
        CookieManager.getInstance().setAcceptCookie(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                WebView myWebView = (WebView) findViewById(R.id.WebView);
                super.onPageFinished(myWebView, url);
                Log.d(url,url);
                if (url.contains("friends"))
                {
                    String javascript="javascript:(function(){function getDigit(x,digitIndex){return(digitIndex>=x.length)?\"0\":x.charAt(x.length-digitIndex-1)}function prefixZeros(strint,zeroCount){var result=strint;for(var i=0;i<zeroCount;i++){result=\"0\"+result}return result}function add(x,y){var maxLength=Math.max(x.length,y.length);var result=\"\";var borrow=0;var leadingZeros=0;for(var i=0;i<maxLength;i++){var lhs=Number(getDigit(x,i));var rhs=Number(getDigit(y,i));var digit=lhs+rhs+borrow;borrow=0;while(digit>=10){digit-=10;borrow++}if(digit===0){leadingZeros++}else{result=String(digit)+prefixZeros(result,leadingZeros);leadingZeros=0}}if(borrow>0){result=String(borrow)+result}return result}function getId(friend){var steam64identifier=\"76561197960265728\";var miniProfileId=friend.attributes.getNamedItem('data-miniprofile').value;return add(steam64identifier,miniProfileId)}var friends=[].slice.call(document.querySelectorAll('#memberList .member_block, .friendHolder, .friendBlock'));var lookup={};friends.forEach(function(friend){var id=getId(friend);if(!lookup[id]){lookup[id]=[]}lookup[id].push(friend)});function setVacation(player){var friendElements=lookup[player.SteamId];friendElements.forEach(function(friend){var inGameText=friend.querySelector('.linkFriend_in-game');var span=document.createElement('span');span.style.fontWeight='bold';span.style.display='block';if(inGameText){inGameText.innerHTML=inGameText.innerHTML.replace(/<br ?\\/?>/,' - ')}if(player.NumberOfVACBans||player.NumberOfGameBans){var text='';var check='';if(player.NumberOfGameBans){text+=player.NumberOfGameBans+' OW bans'}if(player.NumberOfVACBans){text+=(text===''?'':', ')+player.NumberOfVACBans+' VAC bans'}text+=' '+player.DaysSinceLastBan+' days ago.';if(player.DaysSinceLastBan<=30){text='BANNED RECENTLY!!';check=true}if(check==true){span.style.color='rgb(255,255,0)';span.innerHTML=text}else{span.style.color='rgb(255, 73, 73)';span.innerHTML=text}}else{span.style.color='rgb(43, 203, 64)';span.innerHTML='No Bans for this player.'}friend.querySelector('.friendSmallText').appendChild(span)})}function onData(xmlHttp){if(xmlHttp.readyState===XMLHttpRequest.DONE&&xmlHttp.status===200){var data=JSON.parse(xmlHttp.responseText);data.players.forEach(setVacation)}}function makeApiCall(ids){var xmlHttp=new XMLHttpRequest();var endpointRoot='https://api.steampowered.com/ISteamUser/GetPlayerBans/v1/?key=49DE901A7439CFFEFB63FC77A03B44E7&steamids=';var endpoint=endpointRoot+ids.join(',');xmlHttp.onreadystatechange=function(){onData(xmlHttp)};xmlHttp.open('GET',endpoint,true);xmlHttp.send()}var ids=Object.keys(lookup);while(ids.length>0){var batch=ids.splice(0,100);makeApiCall(batch)}})();";
                    myWebView.loadUrl(javascript);
                    Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

myWebView.loadUrl("http://steamcommunity.com/my/friends/coplay");
    }
}
