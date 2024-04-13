package com.fe.render;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {
    private int ShaderProgramID;
    private boolean beingUsed = false;
    private String vertexSource;
    private String fragmentSource;
    private String filepath;
    public Shader(String filepath) {
        this.filepath = filepath;
        try {
            String source = new String( Files.readAllBytes( Paths.get(filepath) ) );
            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            // поиск 1-го паттерна (то, что после #type 'паттерн')
            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, eol).trim();

            // поиск 2-го паттерна (то, что после #type 'паттерн')
            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if (firstPattern.equals("vertex")) {
                vertexSource = splitString[1];
            } else if (firstPattern.equals("fragment")) {
                fragmentSource = splitString[1];
            } else {
                throw new IOException( "UNEXPECTED TOKEN '" + firstPattern + "'" );
            }

            if (secondPattern.equals("vertex")) {
                vertexSource = splitString[2];
            } else if (secondPattern.equals("fragment")) {
                fragmentSource = splitString[2];
            } else {
                throw new IOException( "UNEXPECTED TOKEN '" + secondPattern + "'" );
            }

        } catch (IOException e) {
            e.printStackTrace();
            assert false : "ERR: COULDN'T OPEN FILE FOR SHADER: '" + filepath + "'";
        }
    }

    public void compile() {
        int vertexID, fragmentID;

        // Скомпилировать и связать шейдеры
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        // Вкинуть шейдер в видюху
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // проверить на err компиляцию
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println( "ERR: 'defaultShader.glsl'\n\tVertex shader compilation failed!" );
            System.out.println( glGetShaderInfoLog(vertexID, len) );
            assert false : "";
        }

        // Скомпилировать и связать шейдеры
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        // Вкинуть шейдер в видюху
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // проверить на err компиляцию
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println( "ERR: '" + filepath + "'\n\tLinking of shaders failed!" );
            System.out.println( glGetShaderInfoLog(fragmentID, len) );
            assert false : "";
        }

        // Связка шейдеров и проверка на err
        ShaderProgramID = glCreateProgram();
        glAttachShader(ShaderProgramID, vertexID);
        glAttachShader(ShaderProgramID, fragmentID);
        glLinkProgram(ShaderProgramID);

        // Чек на связку err
        success = glGetProgrami(ShaderProgramID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(ShaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println( "ERR: '" + filepath + "'\n\tLinking of shaders failed!" );
            System.out.println( glGetProgramInfoLog(ShaderProgramID, len) );
            assert false : "";
        }
    }

    public void use() {
        if (!beingUsed) {
            glUseProgram(ShaderProgramID);
            beingUsed = true;
        }
    }

    public void detach() {
        glUseProgram(0);
        beingUsed = false;
    }

    public void uploadMat4f(String varName, Matrix4f mat4) {
        int varLocation = glGetUniformLocation(ShaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat4.get(matBuffer);
        glUniformMatrix4fv(varLocation, false, matBuffer);
    }

    public void uploadMat3f(String varName, Matrix3f mat3) {
        int varLocation = glGetUniformLocation(ShaderProgramID, varName);
        use();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
        mat3.get(matBuffer);
        glUniformMatrix3fv(varLocation, false, matBuffer);
    }
    public void uploadVec4f(String varN, Vector4f vec) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varN);
        use();
        glUniform4f(varLoc, vec.x, vec.y, vec.z, vec.w);
    }

    public void uploadVec3f(String varN, Vector3f vec) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varN);
        use();
        glUniform3f(varLoc, vec.x, vec.y, vec.z);
    }

    public void uploadVec2f(String varN, Vector2f vec) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varN);
        use();
        glUniform2f(varLoc, vec.x, vec.y);
    }

    public void uploadFloat(String varN, float val) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varN);
        use();
        glUniform1f(varLoc, val);
    }

    public void uploadInt(String varN, int val) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varN);
        use();
        glUniform1i(varLoc, val);
    }

    public void uploadTexture(String varName, int slot) {
        int varLoc = glGetUniformLocation(ShaderProgramID, varName);
        use();
        glUniform1i(varLoc, slot);
    }
}
