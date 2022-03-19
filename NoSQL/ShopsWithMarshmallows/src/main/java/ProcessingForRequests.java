import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ProcessingForRequests {

    private static final String ADD_A_SHOP ="Добавить_магазин";
    private static final String ADD_PRODUCT = "Добавить_товар";
    private static final String SET_THE_PRODUCT = "Выставить_товар";
    private static final String STATISTICS = "Статистика_товаров";

    private ConnectingToMongoDB connecting;

    public ProcessingForRequests() {
        connecting = new ConnectingToMongoDB();
    }

    public void parsRequest(String request){
        String[] req = request.split(" ");
        switch (req[0]){
            case ADD_A_SHOP -> addShop(req);
            case ADD_PRODUCT -> addProduct(req);
            case SET_THE_PRODUCT -> setProduct(req);
            case STATISTICS -> StoreStatistics.getStatistics(connecting.getCollectionShops());
            default -> throw new IllegalArgumentException("no such case: " + req[0]);
        }
    }

    private void setProduct(String[] req) {
        Bson filterProd = Filters.eq("_id", req[1]);
        Document prod = connecting.getCollectionProducts().find(filterProd).first();
        Bson filterShop = Filters.eq("name", req[2]);
        connecting.getCollectionShops().updateOne(filterShop, Updates.push("product", prod));
    }

    private void addProduct(String[] req) {
        if(req == null || req.length != 3){
            throw new IllegalArgumentException("request error");
        }
        Document addProduct = new Document();
        addProduct.append("_id", req[1]).append("price", Integer.parseInt(req[2]));
        connecting.getCollectionProducts().insertOne(addProduct);
    }

    private void addShop(String[] req) {
        if (req == null || req.length!= 2){
            throw new IllegalArgumentException("Request error");
        }
        Document addShopName = new Document();
        addShopName.append("name", req[1]);
        connecting.getCollectionShops().insertOne(addShopName);
    }
}
