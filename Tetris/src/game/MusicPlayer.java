package game;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

	/**
 * 音效播放器類別，負責讀取、播放、暫停與停止音訊檔案。
 * 
 * 支援單次播放與持續循環播放，使用 Java Sound API 中的 Clip 播放音效。
 * 
 * @author lilmu
 * @version 3.02
 */
public class MusicPlayer {
    private Clip clip;

    	/**
     * 播放指定音訊檔案。
     *
     * @param filePath 音訊檔案的檔案路徑
     * @param loop 是否進行無限循環播放，true 為重複播放，false 為播放一次
     */
    public void play(String filePath, boolean loop) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    	/**
     * 停止播放並釋放資源。
     * 若音效正在播放，則立即中止並關閉音源。
     */
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    	/**
     * 暫停目前正在播放的音效。
     * 音效將保留當前播放位置，後續可透過 resume 繼續播放。
     */
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    	/**
     * 從暫停位置繼續播放音效。
     * 若目前音效未播放但已載入，則會從中斷處繼續。
     */
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start();
        }
    }
}
