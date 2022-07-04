function addProduct(event){
    let id = event.getAttribute("id");
    let imageURL = event.parentNode.parentNode.parentNode.childNodes[3].src;
    let name = event.parentNode.parentNode.parentNode.childNodes[7].childNodes[1].childNodes[3].getAttribute('value');
    let price = event.parentNode.parentNode.parentNode.childNodes[7].childNodes[1].childNodes[7].getAttribute('value');
    let products = [];
    if(localStorage.getItem('products')){
        products = JSON.parse(localStorage.getItem('products'));
    }

    if (products[id] !== undefined)
    {
        products[id].count++;
    }
    else
    {
        products[id] = ({'productId' : id, name:name, price:price, image : imageURL, count: 1});

    }

    localStorage.setItem('products', JSON.stringify(products));
}

function loadProduct()
{

    let products = [];
    if(localStorage.getItem('products')){
        products = JSON.parse(localStorage.getItem('products'));
    }
    let price = 0;
    let count = 1;
    for (let element of products) {
        if (element !== null){
            $("#products").append(`<tr>\n" +
            "                <th scope=\"row\" >${count++}</th>\n" +
            "                <td>${element["name"]}</td>\n" +
            "                <td>${element["price"]}</td>\n" +
            "                  <td>${element["count"]}</td>\n" +
            "                <td><a class=\"btn btn-danger\" onclick="removeElement(this,${element["productId"]}, ${element["price"]},${element["count"]})" href=\"#\">Delete</a></td>\n" +
            "            </tr>`);

            price+= (parseInt(element["price"]) * parseInt(element["count"]));
        }

    }
    document.getElementById('price').textContent = price;
}

function removeElement(element, id, price,count)
{

    let products = [];
    if(localStorage.getItem('products')){
        products = JSON.parse(localStorage.getItem('products'));
    }

    for (let element of products) {
        if (element !== null && parseInt(element["productId"]) === parseInt(id)){

            const index = products.indexOf(element);
            if (index > -1) { // only splice array when item is found
                products.splice(index, 1); // 2nd parameter means remove one item only
            }

        }

    }
    localStorage.setItem('products', JSON.stringify(products));

    document.getElementById('price').textContent = parseInt(document.getElementById('price').textContent) - parseInt(price)*parseInt(count);
    element.parentNode.parentNode.remove();


}

function buy()
{
    let products = [];
    if(localStorage.getItem('products')){
        products = JSON.parse(localStorage.getItem('products'));
    }

    let ids = [];
    let counts = [];

    for (let element of products) {
        if (element !== null){
            ids.push(parseInt(element['productId']));
            counts.push(element['count']);

        }

    }

    console.log(ids)
    console.log(counts)

    $.ajax({
        type : "POST",
        url : "/buy",
        data : {
            ids: ids,
            counts: counts
        },
        success : function(response) {
            localStorage.setItem('products', []);
        },
        error : function(e) {
            alert('Error: ' + e);
        }
    });
}