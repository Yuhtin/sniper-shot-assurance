package com.yuhtin;

import com.yuhtin.model.ResultTypes;
import com.yuhtin.model.Shot;
import com.yuhtin.persistance.PersistanceData;

import java.util.*;

/**
 * @author <a href="https://github.com/Yuhtin">Yuhtin</a>
 */
public class ShotAssuranceCalculator {

    private static final List<Shot> SHOTS = new ArrayList<>();

    /**
     * Código para calcular o desempenho do atirador baseado na média euclidiana
     * dos acertos na silhueta do alvo
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Lê a quantidade de tiros que o atirador acertou na silhueta
        System.out.print("Informe a quantidade de tiros que impactaram o alvo: ");

        int quantity = scanner.nextInt();
        if (quantity <= 1) {
            showPerformance(quantity, 0);
            return;
        }

        // Lê as coordenadas dos tiros dados pelo atirador na silhueta e adiciona na lista SHOTS
        for (int shot = 1; shot <= quantity; shot++) {
            System.out.println();
            System.out.println("Tiro " + shot + ":");

            System.out.print("Informe a coordenada da linha (X): ");
            int x = scanner.nextInt();

            System.out.print("Informe a coordenada da coluna (Y): ");
            int y = scanner.nextInt();

            System.out.println(PersistanceData.LINE_SEPARATOR);

            SHOTS.add(new Shot(shot, x, y));
        }

        // Calcular o tempo que o código levou para executar
        long calculationInitialTime = System.currentTimeMillis();

        // Comparar dois tiros (P,Q) para calcular a distância euclidiana
        // entre eles, e depois fazer a média de todas as distâncias encontradas
        int combinations = 0;
        double totalEuclideanDistance = 0;

        for (Shot first : SHOTS) {
            for (Shot second : SHOTS) {
                if (first.getShotId() == second.getShotId()) continue;

                // Calculando a distância euclidiana entre dois tiros
                double sqrt = Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + Math.pow(first.getY() - second.getY(), 2));

                // Somando a distância encontrada
                totalEuclideanDistance += sqrt;
                combinations++;
            }
        }

        // Calculando a média de todas as distâncias encontradas (DM)
        double euclideanAverage = totalEuclideanDistance / combinations;

        // Pegando do cache o valor máximo da média da distância euclidiana (DMmax)
        // baseado na quantidade de acertos na silhueta
        // ** VALORES PERSISTENTES **
        double maxEuclideanAverageDistance = PersistanceData.MAX_EUCLIDIAN_AVERAGE.get(quantity);

        // Calculando o desempenho do atirador usando os valores encontrados (IQT)
        double IQT = 1000 * (quantity / 5d) * (1 - euclideanAverage / maxEuclideanAverageDistance);

        // Mostrando o resultado da performance do atirador
        showPerformance(quantity, IQT);

        System.out.println();
        System.out.println("Tempo para executar o calculo: " + (System.currentTimeMillis() - calculationInitialTime) + "ms");
    }

    public static void showPerformance(int shotQuantity, double impactQuality) {
        // Pega o tipo de resultado baseado na qualidade dos tiros na silhueta
        ResultTypes type = ResultTypes.getFromValue(impactQuality);

        System.out.println("TIROS: " + shotQuantity);
        System.out.println("IMPACTOS: " + shotQuantity);
        System.out.printf("INDICE DE QUALIDADE DO TIRO: %.2f", impactQuality);
        System.out.println();
        System.out.println("RESULTADO: " + type.getMessage());
    }

}