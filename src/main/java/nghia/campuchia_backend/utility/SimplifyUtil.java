package nghia.campuchia_backend.utility;

import java.util.Collections;
import java.util.HashMap;

import static nghia.campuchia_backend.utility.ArithmeticUtil.round;

public class SimplifyUtil {

    //private HashMap<String, Double> usersBalance;
    //static List printBill;

    /*
    public void main(String[] args) {
        parm = new HashMap();
        /*
        Chi +20 -20 -30
        Nghia -10 +40 -30
        Dung -10 -20 +60



        parm.put("Chi", -30.0);
        //parm.put("Nghia", 0);
        parm.put("Dung", 30.0);

        //Passing values to findPath Method.
        simplify(parm);

        // System.out.println(printBill.toString());

    }
    */

    public HashMap<String, HashMap<String, Double>> simplify(HashMap<String, Double> details) {
        //printBill = new ArrayList();

        /*

            {id: {id, amount}, ...}
         */
        HashMap<String, HashMap<String, Double>> resultMap = new HashMap<>();
        while (true) {
            Double Max_Value = Collections.max(details.values());
            Double Min_Value = Collections.min(details.values());
            if (!Max_Value.equals(Min_Value)) {
                String Max_Key = getKeyFromValue(details, Max_Value).toString();
                String Min_Key = getKeyFromValue(details, Min_Value).toString();
                Double result = Max_Value + Min_Value;
                result = round(result, 1);
                if ((result >= 0.0)) {
                    //printBill.add(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Min_Value), 2));
                    //System.out.println(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Min_Value), 2));
                    addInvoice(resultMap, Min_Key, Max_Key, round(Math.abs(Min_Value), 2));
                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, result);
                    details.put(Min_Key, 0.0);
                } else {
                    // printBill.add(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
                    //System.out.println(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
                    addInvoice(resultMap, Min_Key, Max_Key, round(Math.abs(Min_Value), 2));

                    details.remove(Max_Key);
                    details.remove(Min_Key);
                    details.put(Max_Key, 0.0);
                    details.put(Min_Key, result);
                }
            } else {
                break;
            }
        }

        return resultMap;
    }

    private Object getKeyFromValue(HashMap hm, Double value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    private void addInvoice(HashMap<String, HashMap<String, Double>> resultMap, String sender, String receiver, Double amount) {
        resultMap.putIfAbsent(sender, new HashMap<>());
        resultMap.get(sender).put(receiver, resultMap.get(sender).getOrDefault(receiver, 0.0) + amount);
    }

}
