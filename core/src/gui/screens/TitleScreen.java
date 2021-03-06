//package gui.screens;
//
//import com.badlogic.gdx.Game;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import game.Hakd;
//import gui.input.TitleInput;
//import org.python.util.PythonInterpreter;
//
//public class TitleScreen extends HakdScreen {
//    private BitmapFont font;
//    private BitmapFont creditFont;
//
//    private String login = "";
//    private final String userName = "username' OR 'x'='x";
//    private String pass = "";
//
//    private float time = 0f;
//    private int counter = 0; // used to check where in the
//    private int random;
//    private float randomTime;
//    private double alpha = 0;
//    private int loginProgress = 0;
//
//    private Sprite loadingsSprite;
//    private Sprite title;
//
//    private Sound keyClick; // PLACEHOLDER get a better click sound, lower
//    // volume and a bit more depth
//
//    public TitleScreen(Hakd game) { // simple title
//        super(game);
//
//        cam = new OrthographicCamera();
//        ((OrthographicCamera) cam).setToOrtho(false, width, height);
//        cam.update();
//    }
//
//    @Override
//    public void show() {
//        game.setScreen(new MenuScreen(game)); // uncomment to skip title
//
//        font = assets.get(("font/Fonts_0.fnt"), BitmapFont.class);
//        creditFont = assets.get(("font/Fonts_7.fnt"), BitmapFont.class);
//
//        font.setColor(0f, 0f, 0f, 0f);
//        creditFont.setColor(0.0f, 1f, 0.0f, 0.18f);
//        random = (int) (Math.random() * 8 + 8); // just your standard pass lengths
//        randomTime = (float) (Math.random() * 1 + .3); // used for the loading circle timeout
//
//        keyClick = Gdx.audio.newSound(Gdx.files.internal("sounds/KeyClick.ogg"));
//        loadingsSprite = new Sprite(Assets.linearTextures.findRegion("loading"));
//
//        title = new Sprite(Assets.linearTextures.findRegion("title"));
//        title.setScale(width / 1600f, height / 900f);
//        title.setPosition(width / 2f - title.getWidth() / 2, height / 1.6f);
//
//        Gdx.input.setInputProcessor(new TitleInput(game));
//
//        // this should initialize jython, so there is no startup lag later on
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new PythonInterpreter().exec("print \'Starting Jython\'");
//            }
//        }).start();
//    }
//
//    @Override
//    public void render(float delta) {
//        super.render(delta);
//        time += delta;
//
//        batch.begin();
//        title.draw(batch);
//        creditFont.draw(batch, "Created by Ryan Mirman", 2, 14); // TODO replace this with a credits page if more people help out
//
//        login();
//        batch.end();
//    }
//
//    private void login() {
//        switch (loginProgress) { // basically a timeline of the title screen
//            default:
//                alpha += 0.007;
//                font.setColor(0f, 0.7f, 0f, (float) alpha);
//                login = "Login: ";
//                pass = "Password: ";
//
//                if (alpha > 0.99d) {
//                    loginProgress++;
//                }
//                break;
//            case 1:
//                font.setColor(Assets.consoleFontColor);
//                if (time % 1 > Math.random() * 0.6 + .03) {
//                    keyClick.play(.1f);
//
//                    time = 0f;
//                    font.setColor(Assets.consoleFontColor);
//                    login += userName.charAt(counter);
//                    counter++;
//
//                }
//
//                if (counter >= userName.length()) {
//                    loginProgress++;
//                }
//                break;
//            case 2:
//                if (time % 1 > Math.random() * 0.6 + .03) {
//                    time = 0f;
//                    pass += "*";
//                    counter++;
//
//                    keyClick.play(.1f);
//                }
//
//                if (counter >= userName.length() + random) {
//                    loginProgress++;
//                }
//                break;
//            case 3:
//                font.setColor(0.4f, 0.7f, 0.4f, 0.4f);
//
//                if (time > 0.8) {
//                    loginProgress++;
//                }
//                break;
//            case 4:
//                loadingsSprite.setPosition(width / 2 - 48, height / 2.5f + 48);
//                loadingsSprite.setRotation((time % 1) * -360);
//                loadingsSprite.draw(batch);
//
//                if (time > 2 + randomTime) { // case 4 time + case 5 time
//                    game.setScreen(new MenuScreen(game));
//                }
//                break;
//        }
//        font.draw(batch, login, width / 4f, height / 2.5f + 25);
//        font.draw(batch, pass, width / 4f, height / 2.5f);
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//
//        if (keyClick != null) {
//            keyClick.dispose();
//        }
//    }
//}
