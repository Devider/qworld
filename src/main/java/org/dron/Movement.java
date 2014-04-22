package org.dron;

/**
 * Created by PhantomX on 19.04.14.
 */
public interface Movement {
    /**
     * Движение вперёд назад
     * @return
     */
    int pitch();

    /**
     * Движение крен вправо, крен влево
     * @return
     */
    int roll();

    /**
     * Движене поворот вправо, поворот влево
     * @return
     */
    int yaw();
}
