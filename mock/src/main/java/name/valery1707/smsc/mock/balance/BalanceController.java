package name.valery1707.smsc.mock.balance;

import name.valery1707.smsc.Balance;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.mock.BaseController;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class BalanceController extends BaseController<Balance> {
	@Override
	public String path() {
		return "balance.php";
	}

	@Override
	protected Balance handle(HashMap<String, String> params, Request request, Response response) throws ServerError {
		return database().balance(params.get("login"));
	}
}
