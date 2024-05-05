package com.fe.physic;
import com.fe.physic.components.RectCollider;

public class ColliderScripts {
    public static boolean isCollide(RectCollider col_1, RectCollider col_2) {
        if (col_1.TLdot[0] < col_2.TLdot[0] &&
            col_1.TRdot[0] > col_2.TLdot[0] &&
            col_1.TLdot[1] < col_2.DLdot[1] &&
            col_1.DLdot[1] > col_2.TLdot[1])
        {
            return true;
        } else if (col_2.TLdot[0] < col_1.TLdot[0] &&
                col_2.TRdot[0] > col_1.TLdot[0] &&
                col_2.TLdot[1] < col_1.DLdot[1] &&
                col_2.DLdot[1] > col_1.TLdot[1])
            {
                return true;
            }
        return false;
    }

    public static RectCollider getRectColliderByEntity(Entity ent) {
        return ent.getComponent(RectCollider.class);
    }

// TODO: Создать метод getColliderByEntity

//    public static void main(String[] args) {
//        RectCollider test1 = new RectCollider(0, 0, 10, 10);
//        RectCollider test2 = new RectCollider(5, 5, 10, 10);
//        System.out.println(isCollide(test1, test2));
//    }
}
