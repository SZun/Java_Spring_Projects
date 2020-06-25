const baseURL = "http://tsg-vending.herokuapp.com/";

const generateErrorMessage = () =>
  $("#error-messages")
    .append($("<li>"))
    .attr({ class: "list-group-item list-group-item-danger" })
    .text("Error calling web service. Please try again later.");

const clearErrors = () => $("#error-messages").empty().attr({ class: "" });

const selectItem = (id) => $("#purchase-item-id").val(id);

const displayAllItems = (arr) => {
  arr.forEach(({ id, name, price, quantity }) => {
    $("#snacks").append(
      `<div class="col-sm-4">
                <div class="card" onclick="selectItem(${id})">
                <div class="card-body">
                    <h6 class="card-subtitle mb-2 text-muted">${id + 1}</h6>
                    <h5 class="text-center card-title">${name}</h5>
                    <p class="text-center card-text">$${price.toFixed(2)}</p>
                    <p class="text-center card-text">Quantity Left: ${quantity}</p>
                </div>
                </div>
            </div>
              `
    );
  });
};

const getAllItems = async () => {
  $("#snacks").empty();
  let allItems;

  try {
    allItems = await $.ajax({ type: "GET", url: `${baseURL}items` });
    clearErrors();
    displayAllItems(allItems);
  } catch (ex) {
    generateErrorMessage();
  }
};

const getChangeString = (money) => {
  const { quarters, dimes, nickels, pennies } = money;

  let changeStr = "";
  if (quarters && quarters != 0) {
    changeStr += `${quarters} Quarter${quarters > 1 ? "s" : ""}, `;
  }
  if (dimes && dimes != 0) {
    changeStr += `${dimes} Dime${dimes > 1 ? "s" : ""}, `;
  }
  if (nickels && nickels != 0) {
    changeStr += `${nickels} Nickel${nickels > 1 ? "s" : ""}, `;
  }
  if (pennies && pennies != 0) {
    changeStr += `${pennies} ${pennies > 1 ? "Pennies" : "Penny"}`;
  }
  if (changeStr == "") {
    changeStr = "No Change";
  }

  return changeStr;
};

const buyItem = async () => {
  const amount = $("#money").val().trim();
  const id = $("#purchase-item-id").val().trim();

  if (id != "") {
    try {
      const money = await $.ajax({
        type: "POST",
        url: `${baseURL}money/${amount}/item/${id}`,
        data: null,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        datatype: "json",
      });

      clearErrors();
      $("#message").val("Thank You!!!");
      $("#change").val(getChangeString(money));
      $("#purchase-item-id").val("");
      $("#money").val("0.00");
      getAllItems();
    } catch (ex) {
      $("#message").val(ex.responseJSON.message);
      $("#change").val("");
      generateErrorMessage();
    }
  } else {
    generateErrorMessage();
    $("#message").val("Please make a selection");
  }
};

const addMoney = (toAdd = 1) => {
  let currentMoney = parseFloat($("#money").val());
  currentMoney += toAdd;
  currentMoney = currentMoney.toFixed(2);
  $("#money").val(currentMoney);
};

const calcChange = () => {
  let totalPennies = parseFloat($("#money").val()) * 100;

  const quarters = (totalPennies / 25).toFixed(0);
  totalPennies = (totalPennies %= 25).toFixed(0);
  const dimes = (totalPennies / 10).toFixed(0);
  totalPennies = (totalPennies %= 10).toFixed(0);
  const nickels = totalPennies / 5;
  totalPennies = (totalPennies %= 5).toFixed(0);

  const money = {
    quarters,
    dimes,
    nickels,
    pennies: totalPennies,
  };

  $("#change").val(getChangeString(money));
  $("#money").val("0.00");
};

const main = () => {
  getAllItems();
  $("#purchase-btn").click(buyItem);
  $("#add-dollar-btn").click(() => addMoney());
  $("#add-quarter-btn").click(() => addMoney(0.25));
  $("#add-dime-btn").click(() => addMoney(0.1));
  $("#add-nickel-btn").click(() => addMoney(0.05));
  $("#change-return-btn").click(calcChange);
};

main();
