package name.valery1707.smsc.mock.balance;

import name.valery1707.smsc.mock.BaseController;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class BalanceController extends BaseController {
	@Override
	public String path() {
		return "balance.php";
	}

	@Override
	protected Object handle(HashMap<String, String> params, Request request, Response response) throws Exception {
		return database().balance(params.get("login"));
	}
}
