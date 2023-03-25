package ca.bcit.comp2522.termproject.capy.models;

public class EnemyAI {

    private static final double ENEMY_SPEED = 1.0;

    public static void moveTowardsPlayer(Enemy enemy, Player player) {
        double deltaX = player.getSprite().getLayoutX() - enemy.getSprite().getLayoutX();
        double deltaY = player.getSprite().getLayoutY() - enemy.getSprite().getLayoutY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        double moveX = ENEMY_SPEED * deltaX / distance;
        double moveY = ENEMY_SPEED * deltaY / distance;

        enemy.getSprite().setLayoutX(enemy.getSprite().getLayoutX() + moveX);
        enemy.getSprite().setLayoutY(enemy.getSprite().getLayoutY() + moveY);

        enemy.updateSprite();
    }
}
