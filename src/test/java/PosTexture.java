import org.joml.Matrix4f;
import org.joml.Vector3f;

public class PosTexture {
    private Model model;
    public PosTexture() {
        float width = 1f;
        float height = 1f;
        float x = 0f;
        float y = 0f;
        float[] vertices = new float[] {
                x,       y,        0, // TOP LEFT     0
                x+width, y,        0, // TOP RIGHT    1
                x+width, y-height, 0, // BOTTOM RIGHT 2
                x,       y-height, 0, // BOTTOM LEFT  3
        };
        float[] texture = new float[] {
                0,0,
                1,0,
                1,1,
                0,1
        };
        int[] indices = new int[] {
                0,1,2,
                2,3,0
        };
        model = new Model(vertices, texture, indices);
    }
    public void renderTexture(RenderTexture texture, int x, int y, Shader shader, Matrix4f world, Camera cam) {
        shader.bind();
        texture.bind(0);
        Matrix4f tex_pos = new Matrix4f().translate(new Vector3f(x, y, 0));
        Matrix4f target = new Matrix4f();
        cam.getProjection().mul(world, target);
        target.mul(tex_pos);
        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);
        model.render();
    }
}
