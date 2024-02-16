CREATE OR REPLACE FUNCTION format_cca_reasons(reason_input varchar[]) RETURNS  text
          as
          $$
          DECLARE
          l_value varchar;
          r_value varchar;
          _applies_to varchar;
          BEGIN
          FOREACH l_value IN ARRAY reason_input
          LOOP
          CASE
          WHEN l_value = 'VD' THEN _applies_to := E'VD AUTO CCA AS PART OF VOIDING,\n';
          WHEN l_value = '04' THEN _applies_to := E'04 CHANGE IN AGENT,\n';
          WHEN l_value = '03' THEN _applies_to := E'03 CHANGE IN BILLING TYPE,\n';
          WHEN l_value = '02' THEN _applies_to := E'02 CHANGE IN CHARGES - WEIGHT/OTHER CHARGES,\n';
          WHEN l_value = '01' THEN _applies_to := E'01 CHANGE IN GROSS/CHARGEABLE WEIGHT,\n';
          WHEN l_value = '07' THEN _applies_to := E'07 FROM CASS LINK123,\n';
          WHEN l_value = '05' THEN _applies_to := E'05 OTHERS,\n';
          ELSE _applies_to := E'RD REJECTED AS PART OF VOIDING';
          END CASE;
          r_value = concat(_applies_to, r_value);
          END LOOP;
          return r_value;
          end;
          $$
          LANGUAGE plpgsql;