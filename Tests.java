public class Tests {
    public static void main(String[] args) {
        int o = 6;
        MagicSquare square = new MagicSquare(o);

        System.out.println("\nMAGIC SQUARE: \n");
        String space = String.valueOf(String.valueOf(o * o).length() + 1);
        for (int i = 0; i < square.getOrder(); i++) {
            for (int j = 0; j < square.getOrder(); j++) {
                System.out.print(String.format("%" + space + "d", square.getBody()[i][j]));
            }
            System.out.println("");
        }
        
        System.out.println("\nORDER: " + square.getOrder());
        System.out.println("MAGIC CONSTANT: " + square.getMagicConstant());

        System.out.println("\nMORE INFO: ");
        space = String.valueOf(String.valueOf(o).length());
        int sum = 0;

        for (int i = 0; i < square.getOrder(); i++) {
            sum = 0;
            for (int j = 0; j < square.getOrder(); j++) {
                sum += square.getBody()[i][j];
            }
            System.out.print(String.format("%" + space + "s", i + 1));
            System.out.println(") row | sum = " + sum);
        }
        System.out.println("------------------------------");

        for (int i = 0; i < square.getOrder(); i++) {
            sum = 0;
            for (int j = 0; j < square.getOrder(); j++) {
                sum += square.getBody()[j][i];
            }
            System.out.print(String.format("%" + space + "s", i + 1));
            System.out.println(") col | sum = " + sum);
        }
        System.out.println("------------------------------");

        sum = 0;
        for (int i = 0; i < square.getOrder(); i++) {
            sum += square.getBody()[i][i];
        }
        System.out.println("1st diagonal | sum = " + sum);
        System.out.println("------------------------------");

        sum = 0;
        for (int i = 0; i < square.getOrder(); i++) {
            sum += square.getBody()[i][square.getOrder() - i - 1];
        }
        System.out.println("2nd diagonal | sum = " + sum);
        System.out.println("------------------------------");
    }
}