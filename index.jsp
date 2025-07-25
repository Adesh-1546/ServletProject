<!DOCTYPE html>
<html>
<head>
    <title>Get Token</title>
</head>
<body>
    <h2>Request a Service Token</h2>
    <form action="generateToken" method="post">
        Name: <input type="text" name="name" required><br><br>
        Phone: <input type="text" name="mobno" required><br><br>
        Service:
        <select name="service" required>
            <option value="Billing">Billing</option>
            <option value="Support">Support</option>
            <option value="Inquiry">Inquiry</option>
        </select><br><br>
        <input type="submit" value="Get Token">
    </form>
</body>
</html>
