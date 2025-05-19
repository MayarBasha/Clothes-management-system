
package javafinalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
    import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportsManager {

    private static final String ORDER_FILE = "orders.txt";
    private ProductFileHandler productHandler;

    public ReportsManager(ProductFileHandler productHandler) {
        this.productHandler = productHandler;
    }


    public String getMostSoldProduct() {
        Map<String, Integer> productCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            boolean inItems = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Items:")) {
                    inItems = true;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    inItems = false;
                }

                if (inItems && line.startsWith("-")) {
                    String[] parts = line.substring(2).split(" x| = ");
                    String name = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    productCount.put(name, productCount.getOrDefault(name, 0) + quantity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> "📦 Most Sold Product: " + e.getKey() + " with quantity " + e.getValue())
                .orElse("No data available.");
    }


    public String getSoldProductsPerSupplier() {
        Map<String, Integer> supplierSales = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            boolean inItems = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Items:")) {
                    inItems = true;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    inItems = false;
                }

                if (inItems && line.startsWith("-")) {
                    String[] parts = line.substring(2).split(" x| = ");
                    String productName = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    String supplier = productHandler.getSupplierNameByProductName(productName);
                    supplierSales.put(supplier, supplierSales.getOrDefault(supplier, 0) + quantity);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder report = new StringBuilder("📊 Sold Products Per Supplier:\n");
        supplierSales.forEach((supplier, count) ->
            report.append("🧾 ").append(supplier).append(": ").append(count).append(" products\n")
        );
        return report.toString();
    }


    public String getTotalRevenue() {
        double total = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Final Total: ")) {
                    total += Double.parseDouble(line.substring(13).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "💰 Total Revenue: " + total + " EGP";
    }


    public String getOrderCountPerUser() {
        Map<String, Integer> userOrderCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("UserId: ")) {
                    String userId = line.substring(8).trim();
                    userOrderCount.put(userId, userOrderCount.getOrDefault(userId, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder report = new StringBuilder("🧑‍💼 Number of Orders Per User:\n");
        userOrderCount.forEach((user, count) ->
            report.append("🔹 ").append(user).append(": ").append(count).append(" orders\n")
        );
        return report.toString();
    }


    public String getTopOrderingUser() {
        Map<String, Integer> userOrderCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("UserId: ")) {
                    String userId = line.substring(8).trim();
                    userOrderCount.put(userId, userOrderCount.getOrDefault(userId, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userOrderCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> "👑 User with Most Orders: " + e.getKey() + " with " + e.getValue() + " orders")
                .orElse("No data available.");
    }


    public String getTopRevenueUser() {
        Map<String, Double> userRevenue = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            String currentUser = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("UserId: ")) {
                    currentUser = line.substring(8).trim();
                } else if (line.startsWith("Final Total: ") && currentUser != null) {
                    double total = Double.parseDouble(line.substring(13).trim());
                    userRevenue.put(currentUser, userRevenue.getOrDefault(currentUser, 0.0) + total);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userRevenue.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> "🏆 User with Highest Revenue: " + e.getKey() + " totaling " + e.getValue())
                .orElse("No data available.");
    }


    public String viewAllOrders() {
        StringBuilder report = new StringBuilder("📄 All Orders:\n\n");
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                report.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return report.toString();
    }


    public String getRevenueInPeriod(LocalDateTime start, LocalDateTime end) {
        double total = 0;
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            LocalDateTime date = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Date: ")) {
                    date = LocalDateTime.parse(line.substring(6).trim());
                } else if (line.startsWith("Final Total: ") && date != null) {
                    if (!date.isBefore(start) && !date.isAfter(end)) {
                        double totalPrice = Double.parseDouble(line.substring(13).trim());
                        total += totalPrice;
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double average = count > 0 ? total / count : 0;
        return "📅 Period: " + start + " to " + end +
               "\n💰 Total Revenue: " + total +
               "\n📊 Average Revenue: " + average;
    }
}

