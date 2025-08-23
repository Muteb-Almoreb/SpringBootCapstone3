# SpringBootCapstone3 Extra Endpoint for Muteb almoreb



Organizations

| Method | Path                                           | Name                   |
| ------ | ---------------------------------------------- | ---------------------- |
| GET    | `/api/v1/organizations/getOrganizationBy/{id}` | Get organization by ID |



Vendor

| Method | Path                                                    | Name                                        |
| ------ | ------------------------------------------------------- | ------------------------------------------- |
| PUT    | `/api/v1/vendor/{id}/suspend/admin/{adminId}`           | Suspend vendor                              |
| PUT    | `/api/v1/vendor/{id}/reinstate/admin/{adminId}`         | Reinstate vendor                            |

Service Requests

| Method | Path                                                    | Name                                        |
| ------ | ------------------------------------------------------- | ------------------------------------------- |
| GET    | `/api/v1/requests/getServiceRequestById/{id}`           | Get service request by ID                   |
| GET    | `/api/v1/requests/getByOrganizationId/{organizationId}` | List service requests by organization       |
| POST   | `/api/v1/requests/addServiceRequest/{organizationId}`   | Create service request (under organization) |

Contracts
| Method | Path                                                    | Name                                        |
| ------ | ------------------------------------------------------- | ------------------------------------------- |
| GET    | `/api/v1/contract/by-request/{serviceRequestId}`        | Get contract by service request ID          |
| GET    | `/api/v1/contract/by-offer/{offerId}`                   | Get contract by offer ID                    |


Mail

| Method | Path                                             | Name                               |
| ------ | ------------------------------------------------ | ---------------------------------- |
| POST   | `/api/v1/contract/{id}/email`                    | Email contract PDF (plain)         |
| GET    | `/sendMail`                                      | Send email                         |






# Landscaping / Tree‑Care Marketplace — Spring Boot Backend

A backend API for a marketplace that connects **organizations** needing tree/landscaping work with **approved vendors**. Organizations post service requests; vendors submit offers; an offer is accepted; a **contract** is generated as a PDF and emailed; payment is processed via **Moyasar**; an **AI** helper can generate a monthly tree‑care plan.

---

## ✨ Features

* **Organizations & Vendors** with admin approval workflow for vendors
* **Service Requests** (OPEN/CLOSED)
* **Offers** lifecycle: PENDING → ACCEPTED / REJECTED / WITHDRAWN
* **Single accepted offer per request** (auto‑reject others and close request)
* **Contracts**: create from accepted offer, print view, renew with same duration
* **PDF generation** (Adobe PDF Services · Document Merge, DOCX → PDF)
* **Email** (HTML email with attached contract PDF)
* **Payments** (Moyasar). Amount = `offerPrice + 20% platform fee` (SAR; sent in halalas)
* **AI monthly care plan** (Spring AI) tailored for hot/arid climates
* **Vendor leaderboard** (top vendors helper)

---

## 🏗️ Tech Stack

* **Java 17**, **Spring Boot 3.x** (Web, Validation, Data JPA, Mail)
* **MySQL 8** (InnoDB)
* **Lombok** for boilerplate
* **Adobe PDF Services** (Document Merge API)
* **Moyasar Payments** (Cards / MADA / Apple Pay)
* **Spring AI** (provider-configurable)

---


### Status Lifecycles

* **ServiceRequest**: `OPEN` → `CLOSED` (when an offer is accepted)
* **Offer**: `PENDING` → `ACCEPTED` | `REJECTED` | `WITHDRAWN` (only one ACCEPTED per request)
* **Vendor.approvalStatus**: `PENDING` | `APPROVED` | `REJECTED` | `SUSPENDED`
* **Contract.status**: `UNPAID` | `PAID` | `FAILED` (renewal resets to `UNPAID`)

---

## ⚙️ Setup

### Prerequisites

* Java 17+
* Maven 3.9+
* MySQL 8

---

## 💸 Payments (Moyasar)

* **Amount formula**: `amountHalalas = round( (offerPrice * 1.20) * 100 )`
* **Create payment**: service prepares the payment with Moyasar using Basic Auth (`api-key:`)
* **Callback**: Moyasar notifies your backend at `/api/payments/callback` with `transaction_id` and `status`
* **Persistence**: Contract `status` and `transactionId` are updated accordingly

> Ensure `PUBLIC_BASE_URL` is reachable by Moyasar (use HTTPS; expose locally with tools like ngrok during development).

---

## 🧾 Contracts & PDF & Email

* Contract is created **only** from an **ACCEPTED** offer and **exactly one per offer**
* **Print View** builds a DTO graph for PDF merge
* Adobe PDF Services **Document Merge** uses `contract-template.docx` to generate the PDF
* **EmailService** sends an HTML email to the organization with the **PDF attached**

---

## 🤖 AI Monthly Care Plan

* Endpoint generates a **1‑month** care plan in **Markdown**
* Prompt persona: *Senior Plant Engineer* focusing on **hot, arid** climates
* Inputs: **offer description** + **location**; output is structured tasks/week with materials & risk notes

---


## ✅ Business Rules & Guards (highlights)

* Vendors must be **APPROVED** by Admin before meaningful actions
* One **offer per vendor per request** (duplicate guard)
* Accepting an offer:

  * marks it **ACCEPTED**
  * marks all other non‑withdrawn offers **REJECTED**
  * sets the **ServiceRequest → CLOSED**
* Contract creation only if **ACCEPTED** and **no existing contract** for that offer
* Renewal allowed only **after expiry** and by the **vendor owner**


---




