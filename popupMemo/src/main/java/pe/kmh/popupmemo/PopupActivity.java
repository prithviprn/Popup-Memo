package pe.kmh.popupmemo;

/*
 * 팝업 액티비티 라이브러리
 * 제작 : 2013년 7월 9일
 *
 * Copyleft 2013 서동길, 엔젤로이드.
 *
 * 이 라이브러리는 팝업 액티비티를 편하고 빠르게 만들 수 있는 라이브러리입니다.
 * 이 라이브러리를 사용하기 위해서는 상속 후 만드려는 액티비티의 속성을 PopupActivity로 바꿔주세요.
 * 예) public class MainActivity extends PopupActivity
 * 현재 팝업 액티비티 라이브러리로 커스텀 뷰를 포함한 액티비티 뷰를 만들 수 있습니다.
 * 라이브러리를 사용하려고 하면, popupscreen라는 이름을 가진 RelativeLayout를 만들어주시고, 아래 소스를 넣어주세요.
 * <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="300dip" // 크기 조절이 가능합니다
    android:layout_height="240dip" // 크기 조절이 가능합니다
    android:layout_centerInParent="true"
    android:id="@+id/popupscreen" >

    // 여기에 뷰를 넣어주세요. 텍스트 뷰나 에디트 뷰, 웹 뷰 등 액티비티 뷰라면 전부 가능합니다.
    </RelativeLayout>
 * 그리고 AndroidManifest.xml의 액티비티 속성에 아래 소스를 넣어주세요.
 *          android:configChanges="orientation|keyboardHidden"
            android:windowSoftInputMode="stateHidden"
            android:theme="@android:style/Theme.Dialog" 또는
            android:theme="@android:style/Theme.Holo.Dialog"
            (하위 버전 지원시 HoloAnywhere 상속 필요!)

 * 그리고 속성을 설정하면 됩니다.
 *
 * 본 라이브러리는 Apache 2.0 License에 의거합니다.
 * 그 외에, 당신이 이 라이브러리를 사용했다는 점만 명시해 두면 더 좋을 바가 없겠지요!
 * 팝업 액티비티 라이브러리에 대한 리포트는 팝업 액티비티 라이브러리(http://github.com/angeloidteam/PopupActivity)의 이슈에 넣어주세요.
 * 보내주신 Pull Request나 Issue는 라이브러리의 발전에 지대한 영향을 미칩니다.
 */

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class PopupActivity extends Activity {

    /*
     * 지역 변수 설정
     */
    WindowManager.LayoutParams mlp;
    RelativeLayout mla;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        mlp = new WindowManager.LayoutParams();
    }

    /*
     * 레이아웃을 설정합니다.
     * 팝업 액티비티는 RelativeLayout 사용을 권장합니다.
     * @param setContentView(R.layout.popupscreen);
     */
    public void setContentView(Layout layout) {
        setContentView(layout);

    }

    /*
     * 레이아웃 id를 설정합니다.
     * 팝업 액티비티는 RelativeLayout 사용을 권장합니다.
     * @param getLayout();
     */
    public void getLayout() {
        mla = (RelativeLayout) findViewById(R.id.popupscreen);
    }

    /*
     * 팝업 액티비티 이외 부분에 블러 효과를 설정합니다
     * 블러 효과가 짙길 원할 경우 밑 dimAmout = 0.7f 부분을 수정하세요.
     * (0.0f [완전 어둡게] ~ 1.0f [블러 효과 없음])
     * @param isBind(true)
     * @param mlp.dimAmount = 0.5f
     */
    public boolean isBind(Boolean bind) {
        if (bind == true) {
            mlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            mlp.dimAmount = 0.7f;
        }
        return bind;
    }

    /*
     * 윈도우 설정을 마쳤을 때 사용합니다.
     * 보통 setContentView(Layout) 전에 쓰입니다.
     * @param setOutsideTouch(true)
     */
    public void WindowBuild() {
        getWindow().setAttributes(mlp);
    }

    /*
     * 레이아웃을 바깥 쪽 터치가 가능한지 설정합니다.
     * 사용자에 따라 SharedPreferences를 이용한 설정 방법을 선택하세요
     * @param setOutsideTouch(true)
     */
    public boolean setOutsideTouch(Boolean outside) {
        if (!(outside == true)) {
            mlp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        return outside;
    }


    /*
     * 팝업 액티비티의 배경화면을 설정합니다
     * @param setBackground(R.drawable.popupBackground.9);
     */
    public void setBackground(Drawable drawable) {
        mla.setBackground(drawable);
    }

    /*
     * 팝업 액티비티의 배경색상을 설정합니다.
     * @param setBackground(R.color.Red);
     */
    public void setBackground(int color) {
        mla.setBackgroundColor(color);
    }

    /*
     * 팝업 액티비티에 타이틀을 표시할지 선택합니다.
     * @param isOnTitle(false);
     */
    public boolean isOnTitle(boolean title) {
        if (!(title == true)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        return title;
    }

    /*
     * 팝업 액티비티의 타이틀을 설정합니다.
     * @param setTitle("팝업 메모");
     */
    public void setTitle(String title) {
        setTitle(title);
    }
}