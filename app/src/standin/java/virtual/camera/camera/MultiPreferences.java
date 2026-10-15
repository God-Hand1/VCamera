package virtual.camera.camera;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import virtual.camera.app.app.App;

/**
 * Local stand-in for the deleted CameraLib `MultiPreferences` class.
 *
 * <p>The original binary AAR (virtual.camera.camera:camera:1.0.0) is no longer
 * downloadable - its host repository was deleted. This implementation keeps the
 * exact same public API and the exact same preference key contract used by
 * {@code SettingFragment} and {@code DialogUtil}:
 *
 * <ul>
 *   <li>method_type (int: 1=disable, 2=local video, 3=network stream)</li>
 *   <li>video_path_local / video_path_local_final_out</li>
 *   <li>video_path_local_audio_enable</li>
 *   <li>video_path_network / video_path_network_audio_enable</li>
 *   <li>show_start_dialog (boolean)</li>
 * </ul>
 *
 * <p>MODE_MULTI_PROCESS is used so the virtual-app ":agent" processes (where the
 * target apps actually run and where a real camera engine would read these
 * keys) see the same file. If the genuine camera AAR is later dropped into
 * app/libs/camera-*.aar, this whole stand-in source set is excluded from the
 * build automatically and the original implementation takes over.
 */
public class MultiPreferences {

    private static final String PREFS_NAME = "vcamera_settings";

    private static volatile MultiPreferences sInstance;

    public static MultiPreferences getInstance() {
        if (sInstance == null) {
            synchronized (MultiPreferences.class) {
                if (sInstance == null) {
                    sInstance = new MultiPreferences();
                }
            }
        }
        return sInstance;
    }

    private MultiPreferences() {
    }

    @SuppressLint("DEPRECATION") // cross-process reads inside the same package
    private static SharedPreferences prefs() {
        return App.getContext().getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
    }

    public boolean getBoolean(String key, boolean def) {
        return prefs().getBoolean(key, def);
    }

    public void setBoolean(String key, boolean value) {
        prefs().edit().putBoolean(key, value).apply();
    }

    public int getInt(String key, int def) {
        return prefs().getInt(key, def);
    }

    public void setInt(String key, int value) {
        prefs().edit().putInt(key, value).apply();
    }

    public String getString(String key, String def) {
        return prefs().getString(key, def);
    }

    public void setString(String key, String value) {
        prefs().edit().putString(key, value).apply();
    }
}
