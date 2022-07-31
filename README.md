# VerizonEcommerceApplication

## Description

A capstone project from HCL and Verizon to create a mock up e-commerce web application. 

## Latest Update

- ***07/31/22*** Connected to Jira, Sonarcloud, and published to Azure

- ***07/08/22*** Http Session now started and replaces old method of storing login credentials. Logout button fully functional and checks for multiple session invalidates. Product class, table, ProductDao, ProductServlet, and product.list.jsp created. Creates list view for admins. Editing/Creating not implemented yet.

- ***07/07/22*** New Account creation accessible to new users in login page. Log in credentials now saved across pages. Admins can now create admins/ update users to admins while customers cannot. All redirects are correctly working for login, insert, create, update, delete, and select (list).
- ***07/05/22*** Added login page for users to sign in to. Users can log in as admin and be redirected to user-list.jps, or as customers and be redirected to temporary placeholder page. Incorrect login information results in redirect back to login page.

## What To Improve

#### Start on handling products:
- [x] Create products table and POJO
- [x] Implement product-list.jsp to display all products for admins (similar to [user-list.jsp](/WebContent/user-list.jsp))
- [ ] Implement different product list view for customers
- [x] Add 'Product List' Button to [user-list.jsp](/WebContent/user-list.jsp) and redirect to product-list.jsp
- [ ] Create product-form.jsp for admins to create/edit products. 

#### Start on handling order details:

- [ ] Create orderdetails table and POJO
- [ ] Implement order-detail-list.jsp to display all order details
- [ ] Add 'Orders Details' Button to order-list and redirect to order-detail-list.jsp

#### Start on handling orders:

- [ ] Create orders table and POJO
- [ ] Implement order-list.jsp to display all orders
- [ ] Add 'Orders List' Button to [user-list.jsp](/WebContent/user-list.jsp) and redirect to order-list.jsp
