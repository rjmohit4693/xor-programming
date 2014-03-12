package com.xorprogramming.example;

import com.xorprogramming.wallpaper.OpenGLWallpaperScene;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Scene
    implements OpenGLWallpaperScene
{
    private float   vertices[] = { -1.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, };

    private short[] indices    = { 0, 1, 2, 0, 2, 3 };

    private FloatBuffer vertexBuffer;

    private ShortBuffer indexBuffer;


    @Override
    public void update(float deltaT, int width, int height)
    {

    }


    @Override
    public void onSizeChange(GL10 gl10, int newWidth, int newHeight)
    {
    }


    @Override
    public void initialize(GL10 gl10, EGLConfig config)
    {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indexBuffer = ibb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }


    @Override
    public void render(GL10 gl10, int width, int height)
    {
        G
    }


    @Override
    public void dispose()
    {
        // Nothing to dispose
    }

}
