package com.spring.boot.springbootcapstone3.Service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final ChatClient chatClient;

    public AiService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }


    public String generateMonthlyTreePlan(String offerDescription, String location) {
        String prompt = """
            You are a senior Plant Engineer. Use practical horticulture best practices for hot/arid regions when relevant,
            favoring native, drought- and heat-tolerant species for Gulf cities and urban settings.

            GOAL
            - Propose suitable tree species for the given place.
            - Produce a detailed, actionable care schedule for ONE month.

            INPUTS
            - Location: %s
            - Offer description: %s

            OUTPUT (Markdown):
            # Site Assessment
            - Climate/heat/salinity assumptions (state assumptions if not provided)
            - Planting context (street/median, park, courtyard, building setback, coastal, etc.)

            # Recommended Species (3–6 options)
            | Common name | Scientific name | Native/Adapted | Max height | Root behavior | Spacing | Sun | Drought tolerance | Notes (wind/salt/urban) |

            # 4-Week Care Schedule (Day-by-Day Highlights)
            - Week 1: soil prep, planting method, staking, initial irrigation volume/frequency
            - Week 2: irrigation adjustment, mulching depth, pest/disease check
            - Week 3: pruning rules (if any), fertilizer (only if appropriate), weed control
            - Week 4: stress indicators, irrigation tapering (if safe), replacement criteria
            Provide exact quantities where possible (L/tree, cm mulch, g fertilizer), and safety notes for heat.

            # Irrigation Plan (Table)
            | Week | Frequency | Volume per tree | Time of day | Notes |

            # Bill of Materials (Approx.)
            - Trees (qty & size), mulch (m³), compost/soil amendment, stakes & ties, drip/emitters, tools/PPE

            # Risks & Mitigations
            - Heat/wind scorch, salt spray (if coastal), root conflicts, vandalism/traffic, pest hotspots

            # Ongoing (Post-Month)
            - Monthly tasks for the next 3–6 months (brief)

            CONSTRAINTS
            - If space is tight (streets/sidewalks), select columnar/controlled-root species.
            - If coastal/saline, prefer salt-tolerant species.
            - Prefer species already proven in the region (explain why in Notes).

            Write clearly and be specific. If inputs are incomplete, state assumptions and proceed.
            """.formatted(location, offerDescription);



        return chatClient.prompt(prompt).call().content();

    }






}
