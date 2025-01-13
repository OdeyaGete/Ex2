package assignments.ex2;
// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;
    private Ex2Sheet _sheet;



    public SCell(String s) {
        setData(s);
    }

    @Override
    public int getOrder() {
        if (order > 0) {
            return order;
        }
        if(getType() == Ex2Utils.FORM) {
            int[][] depth = _sheet.depth();
            int maxDepth = 0;
            String[] innerCells = line.substring(1).split("[+\\-*/()]");
            for (int i = 0; i < innerCells.length; i++) {
                String innerCell = innerCells[i];
                if (innerCell.matches("[A-Z][0-9]+")) {
                    int x = _sheet.xCell(innerCell);
                    int y = _sheet.yCell(innerCell);
                    if (_sheet.isIn(x, y)) {
                        maxDepth = Math.max(maxDepth, depth[x][y]);
                    }
                }
            }
            order = maxDepth + 1;
            return order;
        }
        return 0;
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String s) {
        this.line = s;
        if(s.length() > 0) {
            if (s.charAt(0) == '=') {
                type = Ex2Utils.FORM;
            } else if (isNumber(s)) {
                type = Ex2Utils.NUMBER;
            } else {
                type = Ex2Utils.TEXT;
            }
        }

    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
       this.type = t;
    }

    @Override
    public void setOrder(int t) {
        this.order = t;

    }
    private boolean isNumber (String text){
        try {
            Double.parseDouble(text);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public int xCell(String c){
        if(c == null  || c.isEmpty()) {
            return -1;
        }
        c= c.toUpperCase();
        int index = 0;
        int letterCount=0;
        for(int i=0; i<c.length(); i++) {
            char ch = c.charAt(i);
            if(ch>= 'A'&& ch<= 'Z') {
                index = index*26+(ch-'A'+1);
                letterCount++;
            }
            if(letterCount >1) {
                return -1;
            }
            else{
                continue;
            }
        }
        return index-1;
    }
    // computes the y coordinate of the string
    public int yCell(String c){
        if(c == null  || c.isEmpty()) {
            return -1;
        }
        c=c.toUpperCase();
        for(int i=0; i<c.length(); i++) {
            char ch = c.charAt(i);
            if(ch>= 'A' && ch<= 'Z') {
                c= c.replace(ch, '0');
            }
            else {
                continue;
            }

        }
        return Integer.parseInt(c);
    }

}
