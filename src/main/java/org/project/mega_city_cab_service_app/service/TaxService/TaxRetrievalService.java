        package org.project.mega_city_cab_service_app.service.TaxService;

        import org.project.mega_city_cab_service_app.model.tax.Tax;

        import java.util.List;

        public class TaxRetrievalService {
            private final TaxService taxService;

            public TaxRetrievalService(TaxService taxService) {
                this.taxService = taxService;
            }
            /**
             * Retrieves all taxes.
             *
             * @return A JSON response containing all tax records.
             */
            public String getAllTaxes() {
                List<Tax> taxes = taxService.getAllTaxes();
                if (taxes.isEmpty()) {
                    return "{\"message\": \"No taxes found.\"}";
                }

                StringBuilder json = new StringBuilder("[");
                for (Tax tax : taxes) {
                    json.append(
                            "{\"taxId\": " + tax.getTaxId() +
                                    ", \"description\": \"" + escapeJsonString(tax.getDescription()) + "\"" +
                                    ", \"taxRate\": " + tax.getTaxRate() +
                                    ", \"status\": " + tax.isStatus() + "},"
                    );
                }
                if (!taxes.isEmpty()) {
                    json.deleteCharAt(json.length() - 1); // Remove trailing comma
                }
                json.append("]");
                return json.toString();
            }
            /**
             * Retrieves a tax by its ID.
             *
             * @param taxId The ID of the tax to retrieve.
             * @return A JSON response containing the tax details or an error message.
             */
            public String getTaxById(int taxId) {
                Tax tax = taxService.getTaxById(taxId);
                if (tax != null) {
                    return "{\"taxId\": " + tax.getTaxId() +
                            ", \"description\": \"" + escapeJsonString(tax.getDescription()) + "\"" +
                            ", \"taxRate\": " + tax.getTaxRate() +
                            ", \"status\": " + tax.isStatus() + "}";
                } else {
                    return "{\"error\": \"Tax not found.\"}";
                }
            }
            /**
             * Escapes special characters in a string for JSON compatibility.
             *
             * @param str The string to escape.
             * @return The escaped string.
             */
            private String escapeJsonString(String str) {
                if (str == null) {
                    return "";
                }
                return str.replace("\\", "\\\\")
                        .replace("\"", "\\\"")
                        .replace("\n", "\\n")
                        .replace("\r", "\\r")
                        .replace("\t", "\\t");
            }
        }